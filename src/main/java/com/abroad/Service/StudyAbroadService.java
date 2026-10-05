package com.abroad.Service;

import com.abroad.DTO.StudyAbroadRequestDTO;
import com.abroad.DTO.StudyAbroadResponseDTO;

import java.util.List;

public interface StudyAbroadService {

    StudyAbroadResponseDTO createStudyAbroad(
            StudyAbroadRequestDTO request,
            String role,
            String email
    );

    StudyAbroadResponseDTO getStudyAbroadByCountryId(
            Long countryId);

    List<StudyAbroadResponseDTO> getAllStudyAbroad(
            String role,
            String email
    );

    StudyAbroadResponseDTO updateStudyAbroad(
            Long id,
            StudyAbroadRequestDTO request,
            String role,
            String email
    );

    void deleteStudyAbroad(
            Long id,
            String role,
            String email
    );
}