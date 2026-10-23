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

    List<AbroadJobRoleResponseDTO> getAll();

    List<AbroadJobRoleResponseDTO> getBySector(Long sectorId);

    AbroadJobRoleResponseDTO getById(Long id);

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