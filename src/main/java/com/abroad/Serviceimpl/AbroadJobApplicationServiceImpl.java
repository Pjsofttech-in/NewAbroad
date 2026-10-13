package com.abroad.Serviceimpl;

import com.abroad.DTO.AbroadJobApplicationRequestDTO;
import com.abroad.DTO.AbroadJobApplicationResponseDTO;
import com.abroad.Entity.AbroadJob;
import com.abroad.Entity.AbroadJobApplication;
import com.abroad.Enum.JobApplicationStatus;
import com.abroad.Repository.AbroadJobApplicationRepository;
import com.abroad.Repository.AbroadJobRepository;
import com.abroad.Service.AbroadJobApplicationService;
import com.abroad.Service.PermissionService;
import com.abroad.Service.S3Service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Locale;

@Service
public class AbroadJobApplicationServiceImpl
        implements AbroadJobApplicationService {

    private final AbroadJobApplicationRepository applicationRepository;
    private final AbroadJobRepository jobRepository;
    private final S3Service s3Service;
    private final PermissionService permissionService;

    public AbroadJobApplicationServiceImpl(
            AbroadJobApplicationRepository applicationRepository,
            AbroadJobRepository jobRepository,
            S3Service s3Service,
            PermissionService permissionService) {

        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.s3Service = s3Service;
        this.permissionService = permissionService;
    }

    // Public endpoint: applicants do not need ADMIN permissions to apply.
    @Override
    @Transactional
    public AbroadJobApplicationResponseDTO createApplication(
            AbroadJobApplicationRequestDTO request,
            MultipartFile resume) {

        validateApplication(request, resume);

        AbroadJob job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Job not found"));

        if (!Boolean.TRUE.equals(job.getActive())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Applications are not accepted for this job");
        }

        String email = request.getEmail().trim().toLowerCase(Locale.ROOT);

        if (applicationRepository.existsByJobIdAndEmailIgnoreCase(
                request.getJobId(), email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "You have already applied for this job");
        }

        String resumeUrl;

        try {
            resumeUrl = s3Service.uploadResume(resume);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } catch (IOException | RuntimeException ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Resume upload failed", ex);
        }

        AbroadJobApplication application = new AbroadJobApplication();
        application.setJob(job);
        application.setApplicantName(request.getApplicantName().trim());
        application.setEmail(email);
        application.setPhone(request.getPhone().trim());
        application.setResumeUrl(resumeUrl);
        application.setCoverLetter(request.getCoverLetter());
        application.setStatus(JobApplicationStatus.PENDING);
        application.setActive(true);

        try {
            AbroadJobApplication saved =
                    applicationRepository.saveAndFlush(application);

            return toResponse(saved);

        } catch (DataIntegrityViolationException ex) {
            // Prevent an uploaded file being left behind if the
            // database's unique constraint rejects a duplicate.
            try {
                s3Service.deleteImage(resumeUrl);
            } catch (RuntimeException ignored) {
                // Log this failure in production so the orphaned
                // S3 object can be cleaned up.
            }

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "An application already exists for this job and email",
                    ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AbroadJobApplicationResponseDTO> getAllApplications(
            int page,
            int size,
            String role,
            String email) {

        checkPermission(role, email, "GET");

        if (page < 0 || size < 1 || size > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Page must be >= 0 and size must be between 1 and 100");
        }

        return applicationRepository.findAll(
                PageRequest.of(
                        page,
                        size,
                        Sort.by(Sort.Direction.DESC, "appliedAt")
                )
        ).map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public AbroadJobApplicationResponseDTO getApplicationById(
            Long id,
            String role,
            String email) {

        checkPermission(role, email, "GET");

        return toResponse(findApplication(id));
    }

    @Override
    @Transactional
    public AbroadJobApplicationResponseDTO updateStatus(
            Long id,
            JobApplicationStatus status,
            String role,
            String email) {

        checkPermission(role, email, "PUT");

        if (status == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Application status is required");
        }

        AbroadJobApplication application = findApplication(id);
        application.setStatus(status);

        return toResponse(applicationRepository.save(application));
    }

    @Override
    @Transactional
    public void deactivateApplication(
            Long id,
            String role,
            String email) {

        checkPermission(role, email, "DELETE");

        AbroadJobApplication application = findApplication(id);
        application.setActive(false);
        applicationRepository.save(application);
    }

    private void validateApplication(
            AbroadJobApplicationRequestDTO request,
            MultipartFile resume) {

        if (request == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Application details are required");
        }

        if (request.getJobId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Job ID is required");
        }

        if (isBlank(request.getApplicantName())
                || request.getApplicantName().trim().length() > 150) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Applicant name is required and must not exceed 150 characters");
        }

        if (isBlank(request.getEmail())
                || request.getEmail().trim().length() > 150
                || !request.getEmail().trim().matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "A valid email is required");
        }

        if (isBlank(request.getPhone())
                || request.getPhone().trim().length() > 20) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Phone number is required and must not exceed 20 characters");
        }

        if (resume == null || resume.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Resume file is required");
        }
    }

    private AbroadJobApplication findApplication(Long id) {
        AbroadJobApplication application =
                applicationRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Job application not found"));

        if (!Boolean.TRUE.equals(application.getActive())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Job application not found");
        }

        return application;
    }

    private void checkPermission(
            String role,
            String email,
            String action) {

        if (isBlank(role) || isBlank(email)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Role and email are required");
        }

        if (!permissionService.hasPermission(
                role.trim(), email.trim(), action)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You do not have permission to perform this action");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private AbroadJobApplicationResponseDTO toResponse(
            AbroadJobApplication application) {

        AbroadJob job = application.getJob();

        return new AbroadJobApplicationResponseDTO(
                application.getId(),
                job.getId(),
                job.getJobTitle(),
                application.getApplicantName(),
                application.getEmail(),
                application.getPhone(),
                application.getResumeUrl(),
                application.getCoverLetter(),
                application.getStatus(),
                application.getAppliedAt(),
                application.getActive()
        );
    }


    @Override
    public Page<AbroadJobApplicationResponseDTO> searchApplications(
            Long jobId,
            JobApplicationStatus status,
            String applicantEmail,
            int page,
            int size,
            String role,
            String email) {

        checkPermission(role, email, "GET");

        if (page < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Page must be zero or greater");
        }

        if (size < 1 || size > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Size must be between 1 and 100");
        }

        String normalizedEmail = applicantEmail == null
                || applicantEmail.isBlank()
                ? null
                : applicantEmail.trim();

        Pageable pageable = PageRequest.of(
                page, size, Sort.by(Sort.Direction.DESC, "appliedAt"));

        return applicationRepository.searchApplications(
                        jobId, status, normalizedEmail, pageable)
                .map(this::toResponse);
    }

}