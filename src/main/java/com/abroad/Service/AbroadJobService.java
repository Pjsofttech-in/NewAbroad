
package com.abroad.Service;

import com.abroad.DTO.AbroadJobRequestDTO;
import com.abroad.DTO.AbroadJobResponseDTO;

import org.springframework.data.domain.Page;
import java.math.BigDecimal;


import java.util.List;

public interface AbroadJobService {

    AbroadJobResponseDTO createJob(
            AbroadJobRequestDTO request,
            String role,
            String email
    );

    List<AbroadJobResponseDTO> getAllJobs();

    AbroadJobResponseDTO getJobById(Long id);

    AbroadJobResponseDTO updateJob(
            Long id,
            AbroadJobRequestDTO request,
            String role,
            String email
    );

    void deleteJob(
            Long id,
            String role,
            String email
    );

    Page<AbroadJobResponseDTO> searchJobs(
            String keyword,
            Long educationId,
            Long sectorId,
            Long jobRoleId,
            Long locationId,
            Long jobTypeId,
            BigDecimal minSalary,
            BigDecimal maxSalary,
            Boolean active,
            int page,
            int size,
            String sortBy,
            String sortDir,
            String role,
            String email
    );
}