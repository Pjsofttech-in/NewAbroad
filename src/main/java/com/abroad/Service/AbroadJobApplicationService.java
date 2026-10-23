package com.abroad.Service;

import com.abroad.DTO.AbroadJobApplicationRequestDTO;
import com.abroad.DTO.AbroadJobApplicationResponseDTO;
import com.abroad.Enum.JobApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface AbroadJobApplicationService {

    AbroadJobApplicationResponseDTO createApplication(
            AbroadJobApplicationRequestDTO request,
            MultipartFile resume
    );

    Page<AbroadJobApplicationResponseDTO> getAllApplications(
            int page,
            int size,
            String role,
            String email
    );

    AbroadJobApplicationResponseDTO getApplicationById(
            Long id,
            String role,
            String email
    );

    AbroadJobApplicationResponseDTO updateStatus(
            Long id,
            JobApplicationStatus status,
            String Remark,
            String role,
            String email
    );

    void deactivateApplication(
            Long id,
            String role,
            String email
    );

    Page<AbroadJobApplicationResponseDTO> searchApplications(
            Long jobId,
            JobApplicationStatus status,
            String applicantEmail,
            int page,
            int size,
            String role,
            String email
    );
}