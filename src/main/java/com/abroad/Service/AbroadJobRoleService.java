package com.abroad.Service;

import com.abroad.DTO.AbroadJobRoleResponseDTO;
import com.abroad.Entity.AbroadJobRole;

import java.util.List;

public interface AbroadJobRoleService {

    AbroadJobRoleResponseDTO create(
            AbroadJobRole jobRole,
            Long sectorId,
            String role,
            String email
    );

    List<AbroadJobRoleResponseDTO> getAll(
            String role,
            String email
    );

    List<AbroadJobRoleResponseDTO> getBySector(
            Long sectorId,
            String role,
            String email
    );

    AbroadJobRoleResponseDTO getById(
            Long id,
            String role,
            String email
    );

    AbroadJobRoleResponseDTO update(
            Long id,
            AbroadJobRole jobRole,
            Long sectorId,
            String role,
            String email
    );

    void delete(
            Long id,
            String role,
            String email
    );
}