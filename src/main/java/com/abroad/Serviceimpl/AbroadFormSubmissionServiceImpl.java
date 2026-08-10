package com.abroad.Serviceimpl;


import com.abroad.DTO.AbroadFormSubmissionDTO;
import com.abroad.DTO.AbroadFormSubmissionResponseDTO;
import com.abroad.Entity.AbroadContinent;
import com.abroad.Entity.AbroadCourse;
import com.abroad.Entity.AbroadFormSubmission;

import com.abroad.Enum.FormType;
import com.abroad.Repository.AbroadFormSubmissionRepository;
import com.abroad.Repository.ContinentRepository;
import com.abroad.Repository.CourseRepository;
import com.abroad.Service.AbroadFormSubmissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AbroadFormSubmissionServiceImpl
        implements AbroadFormSubmissionService {

    private final AbroadFormSubmissionRepository formSubmissionRepository;

    private final ContinentRepository continentRepository;

    private final CourseRepository courseRepository;

    private final PermissionServiceImpl permissionService;

    private final PasswordEncoder passwordEncoder;


    @Override
    public AbroadFormSubmissionResponseDTO saveFormSubmission(AbroadFormSubmissionDTO dto) {

        AbroadFormSubmission submission = new AbroadFormSubmission();

        submission.setFormType(dto.getFormType());

        submission.setName(dto.getName());
        submission.setEmail(dto.getEmail());
        submission.setPhoneNumber(dto.getPhoneNumber());

        if (dto.getContinentId() != null) {

            AbroadContinent continent =
                    continentRepository.findById(dto.getContinentId())
                            .orElseThrow(() ->
                                    new RuntimeException("Continent not found with id: " + dto.getContinentId()));

            submission.setAbroadContinent(continent);
        }

        if (dto.getCourseId() != null) {

            AbroadCourse course =
                    courseRepository.findById(dto.getCourseId())
                            .orElseThrow(() ->
                                    new RuntimeException("Course not found with id: " + dto.getCourseId()));

            submission.setAbroadCourse(course);
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            submission.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        submission.setCreatedByEmail(dto.getCreatedByEmail());
        submission.setRole(dto.getRole());
        submission.setBranchCode(dto.getBranchCode());
        submission.setStatus(dto.getStatus());

        AbroadFormSubmission savedSubmission = formSubmissionRepository.save(submission);

          return convertToResponseDTO(savedSubmission);
    }

    private AbroadFormSubmissionResponseDTO convertToResponseDTO(AbroadFormSubmission submission) {

        AbroadFormSubmissionResponseDTO response = new AbroadFormSubmissionResponseDTO();

        response.setId(submission.getId());

        response.setFormType(submission.getFormType());

        response.setName(submission.getName());

        response.setEmail(submission.getEmail());

        response.setPhoneNumber(submission.getPhoneNumber());

        response.setStatus(submission.getStatus());

        response.setCreatedByEmail(submission.getCreatedByEmail());

        response.setRole(submission.getRole());

        response.setBranchCode(submission.getBranchCode());


        if (submission.getAbroadContinent() != null) {

            response.setContinentId(submission.getAbroadContinent().getId());

            response.setContinentName(submission.getAbroadContinent().getContinentname());
        }


        if (submission.getAbroadCourse() != null) {

            response.setCourseId(submission.getAbroadCourse().getId());

            response.setCourseName(submission.getAbroadCourse().getCourseName());
        }

        return response;
    }

    @Override
    public List<AbroadFormSubmissionResponseDTO> getAllFormSubmissions(String role, String email) {

        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view form submissions");
        }

        List<AbroadFormSubmission> submissions = formSubmissionRepository.findAll();

        return submissions.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }


    private AbroadFormSubmissionResponseDTO mapToResponseDTO(AbroadFormSubmission submission) {

        AbroadFormSubmissionResponseDTO dto = new AbroadFormSubmissionResponseDTO();

        dto.setId(submission.getId());
        dto.setFormType(submission.getFormType());
        dto.setName(submission.getName());
        dto.setEmail(submission.getEmail());
        dto.setPhoneNumber(submission.getPhoneNumber());
        dto.setStatus(submission.getStatus());
        dto.setCreatedByEmail(submission.getCreatedByEmail());
        dto.setRole(submission.getRole());
        dto.setBranchCode(submission.getBranchCode());

        if (submission.getAbroadContinent() != null) {
            dto.setContinentId(submission.getAbroadContinent().getId());
            dto.setContinentName(submission.getAbroadContinent().getContinentname());
        }

        if (submission.getAbroadCourse() != null) {
            dto.setCourseId(submission.getAbroadCourse().getId());
            dto.setCourseName(submission.getAbroadCourse().getCourseName());
        }

        return dto;
    }


    @Override
    public List<AbroadFormSubmissionResponseDTO> getFormSubmissionByType(FormType formType, String role, String email) {

        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view form submissions");
        }

        List<AbroadFormSubmission> submissions =
                formSubmissionRepository.findByFormType(formType);

        return submissions.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public AbroadFormSubmissionResponseDTO getFormSubmissionById(Long id, String role, String email) {

        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view form submission");
        }

        AbroadFormSubmission submission = formSubmissionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Form submission not found"));

        return convertToResponseDTO(submission);
    }

    @Override
    public AbroadFormSubmissionResponseDTO updateFormSubmission(Long id, AbroadFormSubmissionDTO dto,
                                                                String role,String email) {

        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update form submission");
        }

        AbroadFormSubmission submission = formSubmissionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Form submission not found"));

        if (dto.getName() != null) {
            submission.setName(dto.getName());
        }

        if (dto.getEmail() != null) {
            submission.setEmail(dto.getEmail());
        }

        if (dto.getPhoneNumber() != null) {
            submission.setPhoneNumber(dto.getPhoneNumber());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            submission.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getStatus() != null) {
            submission.setStatus(dto.getStatus());
        }

        if (dto.getFormType() != null) {
            submission.setFormType(dto.getFormType());
        }

        if (dto.getContinentId() != null) {

            AbroadContinent continent = continentRepository.findById(dto.getContinentId())
                    .orElseThrow(() ->
                            new RuntimeException("Continent not found"));

            submission.setAbroadContinent(continent);
        }

        if (dto.getCourseId() != null) {

            AbroadCourse course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() ->
                            new RuntimeException("Course not found"));

            submission.setAbroadCourse(course);
        }

        AbroadFormSubmission updated = formSubmissionRepository.save(submission);

        return convertToResponseDTO(updated);
    }

    @Override
    public void deleteFormSubmission(Long id, String role, String email) {

        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete form submission");
        }

        AbroadFormSubmission submission = formSubmissionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Form submission not found"));

        formSubmissionRepository.delete(submission);
    }
}

