package com.abroad.Service;

import com.abroad.DTO.AbroadJobSectorResponseDTO;
import com.abroad.Entity.AbroadJobSector;

import java.util.List;

public interface AbroadJobSectorService {

    AbroadJobSectorResponseDTO create(
            AbroadJobSector sector,
            Long educationId,
            String role,
            String email);

    List<AbroadJobSectorResponseDTO> getAll(
            String role,
            String email);

    List<AbroadJobSectorResponseDTO> getByEducation(
            Long educationId,
            String role,
            String email);

    AbroadJobSectorResponseDTO getById(
            Long id,
            String role,
            String email);

    AbroadJobSectorResponseDTO update(
            Long id,
            AbroadJobSector sector,
            Long educationId,
            String role,
            String email);

    void delete(
            Long id,
            String role,
            String email);
}