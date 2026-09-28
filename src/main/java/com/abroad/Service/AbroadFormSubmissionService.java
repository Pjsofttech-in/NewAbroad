package com.abroad.Service;

import com.abroad.DTO.AbroadFormSubmissionDTO;
import com.abroad.DTO.AbroadFormSubmissionResponseDTO;
import com.abroad.Enum.FormType;

import java.util.List;

public interface AbroadFormSubmissionService {


    AbroadFormSubmissionResponseDTO saveFormSubmission(AbroadFormSubmissionDTO dto);


    List<AbroadFormSubmissionResponseDTO> getAllFormSubmissions(
            String role,
            String email);

    List<AbroadFormSubmissionResponseDTO> getFormSubmissionByType(
            FormType formType,
            String role,
            String email);

    AbroadFormSubmissionResponseDTO getFormSubmissionById(
            Long id,
            String role,
            String email);

    AbroadFormSubmissionResponseDTO updateFormSubmission(
            Long id,
            AbroadFormSubmissionDTO dto,
            String role,
            String email);

    void deleteFormSubmission(
            Long id,
            String role,
            String email);
}
