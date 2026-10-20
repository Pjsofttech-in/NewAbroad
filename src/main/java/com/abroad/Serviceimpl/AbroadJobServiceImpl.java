package com.abroad.Serviceimpl;

import com.abroad.DTO.AbroadJobRequestDTO;
import com.abroad.DTO.AbroadJobResponseDTO;
import com.abroad.Entity.*;
import com.abroad.Repository.*;
import com.abroad.Service.AbroadJobService;
import com.abroad.Service.PermissionService;
import com.abroad.Pagination.AbroadJobSearchSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AbroadJobServiceImpl implements AbroadJobService {

    private final AbroadJobRepository jobRepository;
    private final AbroadJobEducationRepository educationRepository;
    private final AbroadJobSectorRepository sectorRepository;
    private final AbroadJobRoleRepository roleRepository;
    private final AbroadJobLocationRepository locationRepository;
    private final AbroadJobCurrencyRepository currencyRepository;
    private final AbroadJobTypeRepository typeRepository;
    private final AbroadJobWorkTypeRepository workTypeRepository;
    private final PermissionService permissionService;

    public AbroadJobServiceImpl(
            AbroadJobRepository jobRepository,
            AbroadJobEducationRepository educationRepository,
            AbroadJobSectorRepository sectorRepository,
            AbroadJobRoleRepository roleRepository,
            AbroadJobLocationRepository locationRepository,
            AbroadJobCurrencyRepository currencyRepository,
            AbroadJobTypeRepository typeRepository,
            AbroadJobWorkTypeRepository workTypeRepository,
            PermissionService permissionService
    ) {
        this.jobRepository = jobRepository;
        this.educationRepository = educationRepository;
        this.sectorRepository = sectorRepository;
        this.roleRepository = roleRepository;
        this.locationRepository = locationRepository;
        this.currencyRepository = currencyRepository;
        this.typeRepository = typeRepository;
        this.workTypeRepository = workTypeRepository;
        this.permissionService = permissionService;
    }

    private void checkPermission(
            String role,
            String email,
            String action
    ) {
        if (role == null || email == null
                || role.isBlank() || email.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Role and email are required"
            );
        }

        if (!permissionService.hasPermission(role, email, action)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No permission to " + action + " Job"
            );
        }
    }

    private <T> T required(
            T entity,
            String entityName,
            Long id
    ) {
        if (entity == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    entityName + " not found with ID: " + id
            );
        }

        return entity;
    }

    private void validateRequest(AbroadJobRequestDTO request) {
        if (request == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Job request body is required"
            );
        }

        if (request.getJobTitle() == null
                || request.getJobTitle().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Job title is required"
            );
        }

        if (request.getSkills() == null
                || request.getSkills().isEmpty()
                || request.getSkills().stream()
                .noneMatch(skill ->
                        skill != null && !skill.isBlank())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "At least one valid skill is required"
            );
        }

        if (request.getOpenings() == null
                || request.getOpenings() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Openings must be greater than zero"
            );
        }

        if (request.getSalary() == null
                || request.getSalary().compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Salary cannot be negative or null"
            );
        }

        if (request.getEducationId() == null
                || request.getSectorId() == null
                || request.getJobRoleId() == null
                || request.getLocationId() == null
                || request.getCurrencyId() == null
                || request.getJobTypeId() == null
                || request.getWorkTypeId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "All seven master IDs, including Work Type, are required"
            );
        }

        if (request.getExperienceLevel() == null
                || request.getExperienceLevel().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Experience Level is required"
            );
        }

        if (request.getExperienceLevel().trim().length() > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Experience Level cannot exceed 100 characters"
            );
        }
    }

    private AbroadJobEducation getEducation(Long id) {
        AbroadJobEducation education = required(
                educationRepository.findById(id).orElse(null),
                "Education",
                id
        );

        if (!Boolean.TRUE.equals(education.getActive())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Selected Education is inactive"
            );
        }

        return education;
    }

    private AbroadJobSector getSector(
            Long id,
            AbroadJobEducation education
    ) {
        AbroadJobSector sector = required(
                sectorRepository.findById(id).orElse(null),
                "Sector",
                id
        );

        if (!Boolean.TRUE.equals(sector.getActive())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Selected Sector is inactive"
            );
        }

        if (sector.getEducation() == null
                || !education.getId().equals(
                sector.getEducation().getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Selected Sector does not belong to Education"
            );
        }

        return sector;
    }

    private AbroadJobRole getJobRole(
            Long id,
            AbroadJobSector sector
    ) {
        AbroadJobRole jobRole = required(
                roleRepository.findById(id).orElse(null),
                "Job Role",
                id
        );

        if (!Boolean.TRUE.equals(jobRole.getActive())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Selected Job Role is inactive"
            );
        }

        if (jobRole.getSector() == null
                || !sector.getId().equals(
                jobRole.getSector().getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Selected Job Role does not belong to Sector"
            );
        }

        return jobRole;
    }

    private AbroadJobLocation getLocation(Long id) {
        AbroadJobLocation location = required(
                locationRepository.findById(id).orElse(null),
                "Location",
                id
        );

        if (!Boolean.TRUE.equals(location.getActive())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Selected Location is inactive"
            );
        }

        if (location.getCountry() == null
                || !Boolean.TRUE.equals(
                location.getCountry().getActive())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Selected Location must belong to an active Country"
            );
        }

        return location;
    }

    private AbroadJobCurrency getCurrency(Long id) {
        AbroadJobCurrency currency = required(
                currencyRepository.findById(id).orElse(null),
                "Currency",
                id
        );

        if (!Boolean.TRUE.equals(currency.getActive())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Selected Currency is inactive"
            );
        }

        return currency;
    }

    private AbroadJobType getJobType(Long id) {
        AbroadJobType jobType = required(
                typeRepository.findById(id).orElse(null),
                "Job Type",
                id
        );

        if (!Boolean.TRUE.equals(jobType.getActive())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Selected Job Type is inactive"
            );
        }

        return jobType;
    }

    private AbroadJobWorkType getWorkType(Long id) {
        AbroadJobWorkType workType = required(
                workTypeRepository.findById(id).orElse(null),
                "Work Type",
                id
        );

        if (!Boolean.TRUE.equals(workType.getActive())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Selected Work Type is inactive"
            );
        }

        return workType;
    }

    private String convertSkillsToString(List<String> skills) {
        if (skills == null) {
            return "";
        }

        String result = skills.stream()
                .filter(skill -> skill != null && !skill.isBlank())
                .map(String::trim)
                .distinct()
                .collect(Collectors.joining(", "));

        if (result.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "At least one valid skill is required"
            );
        }

        return result;
    }

    private List<String> convertSkillsToList(String skills) {
        if (skills == null || skills.isBlank()) {
            return Collections.emptyList();
        }

        return Arrays.stream(skills.split(","))
                .map(String::trim)
                .filter(skill -> !skill.isBlank())
                .toList();
    }

    private void applyRequest(
            AbroadJob job,
            AbroadJobRequestDTO request
    ) {
        validateRequest(request);

        AbroadJobEducation education =
                getEducation(request.getEducationId());

        AbroadJobSector sector =
                getSector(request.getSectorId(), education);

        AbroadJobRole jobRole =
                getJobRole(request.getJobRoleId(), sector);

        job.setJobTitle(request.getJobTitle().trim());
        job.setEducation(education);
        job.setSector(sector);
        job.setJobRole(jobRole);

        job.setSkill(convertSkillsToString(request.getSkills()));
        job.setOpenings(request.getOpenings());

        job.setLocation(getLocation(request.getLocationId()));
        job.setSalary(request.getSalary());
        job.setCurrency(getCurrency(request.getCurrencyId()));
        job.setJobType(getJobType(request.getJobTypeId()));
        job.setWorkType(getWorkType(request.getWorkTypeId()));

        job.setExperienceLevel(request.getExperienceLevel().trim());
        job.setDescription(request.getDescription());
        job.setActive(
                request.getActive() == null
                        ? true
                        : request.getActive()
        );
    }

    private AbroadJobResponseDTO toResponse(AbroadJob job) {
        AbroadJobResponseDTO dto = new AbroadJobResponseDTO();

        dto.setId(job.getId());
        dto.setJobTitle(job.getJobTitle());

        dto.setEducationId(job.getEducation().getId());
        dto.setEducationName(job.getEducation().getName());

        dto.setSectorId(job.getSector().getId());
        dto.setSectorName(job.getSector().getName());

        dto.setJobRoleId(job.getJobRole().getId());
        dto.setJobRoleName(job.getJobRole().getName());

        dto.setSkills(convertSkillsToList(job.getSkill()));
        dto.setOpenings(job.getOpenings());

        dto.setLocationId(job.getLocation().getId());
        dto.setLocationName(job.getLocation().getName());

        AbroadJobCountry country = job.getLocation().getCountry();
        dto.setCountryId(country != null ? country.getId() : null);
        dto.setCountryName(country != null ? country.getName() : null);

        dto.setSalary(job.getSalary());

        dto.setCurrencyId(job.getCurrency().getId());
        dto.setCurrencyName(job.getCurrency().getName());

        dto.setJobTypeId(job.getJobType().getId());
        dto.setJobTypeName(job.getJobType().getName());

        AbroadJobWorkType workType = job.getWorkType();
        dto.setWorkTypeId(workType != null ? workType.getId() : null);
        dto.setWorkTypeName(workType != null ? workType.getName() : null);

        dto.setExperienceLevel(job.getExperienceLevel());
        dto.setDescription(job.getDescription());
        dto.setActive(job.getActive());

        return dto;
    }

    @Override
    public AbroadJobResponseDTO createJob(
            AbroadJobRequestDTO request,
            String role,
            String email
    ) {
        checkPermission(role, email, "POST");

        AbroadJob job = new AbroadJob();
        applyRequest(job, request);

        return toResponse(jobRepository.save(job));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AbroadJobResponseDTO> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AbroadJobResponseDTO getJobById(Long id) {
        AbroadJob job = required(
                jobRepository.findById(id).orElse(null),
                "Job",
                id
        );

        return toResponse(job);
    }

    @Override
    public AbroadJobResponseDTO updateJob(
            Long id,
            AbroadJobRequestDTO request,
            String role,
            String email
    ) {
        checkPermission(role, email, "PUT");

        AbroadJob job = required(
                jobRepository.findById(id).orElse(null),
                "Job",
                id
        );

        applyRequest(job, request);

        return toResponse(jobRepository.save(job));
    }

    @Override
    public void deleteJob(
            Long id,
            String role,
            String email
    ) {
        checkPermission(role, email, "DELETE");

        AbroadJob job = required(
                jobRepository.findById(id).orElse(null),
                "Job",
                id
        );

        jobRepository.delete(job);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AbroadJobResponseDTO> searchJobs(
            String keyword,
            Long educationId,
            Long sectorId,
            Long jobRoleId,
            Long locationId,
            Long jobTypeId,
            Long workTypeId,
            String experienceLevel,
            BigDecimal minSalary,
            BigDecimal maxSalary,
            Boolean active,
            int page,
            int size,
            String sortBy,
            String sortDir,
            String role,
            String email
    ) {
        checkPermission(role, email, "GET");

        if (page < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Page must be zero or greater"
            );
        }

        if (size < 1 || size > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Page size must be between 1 and 100"
            );
        }

        if ((minSalary != null && minSalary.signum() < 0)
                || (maxSalary != null && maxSalary.signum() < 0)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Salary filters cannot be negative"
            );
        }

        if (minSalary != null && maxSalary != null
                && minSalary.compareTo(maxSalary) > 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Minimum salary cannot exceed maximum salary"
            );
        }

        Set<String> allowedSortFields = Set.of(
                "id",
                "jobTitle",
                "salary",
                "openings",
                "experienceLevel"
        );

        if (sortBy == null || !allowedSortFields.contains(sortBy)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "sortBy must be one of: id, jobTitle, salary, openings, experienceLevel"
            );
        }

        Sort.Direction direction;
        try {
            direction = Sort.Direction.fromString(sortDir);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "sortDir must be ASC or DESC"
            );
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(direction, sortBy)
        );

        var specification = AbroadJobSearchSpecification.build(
                keyword,
                educationId,
                sectorId,
                jobRoleId,
                locationId,
                jobTypeId,
                workTypeId,
                experienceLevel,
                minSalary,
                maxSalary,
                active
        );

        return jobRepository.findAll(specification, pageable)
                .map(this::toResponse);
    }
}