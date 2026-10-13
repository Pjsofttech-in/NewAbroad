package com.abroad.Serviceimpl;

import com.abroad.DTO.AbroadJobRoleResponseDTO;
import com.abroad.Entity.AbroadJobRole;
import com.abroad.Entity.AbroadJobSector;
import com.abroad.Repository.AbroadJobRoleRepository;
import com.abroad.Repository.AbroadJobSectorRepository;
import com.abroad.Service.AbroadJobRoleService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbroadJobRoleServiceImpl
        implements AbroadJobRoleService {

    @Autowired
    private AbroadJobRoleRepository repository;

    @Autowired
    private AbroadJobSectorRepository sectorRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadJobRoleResponseDTO create(
            AbroadJobRole jobRole,
            Long sectorId,
            String role,
            String email) {

        checkPermission(role, email, "POST");

        validateName(jobRole.getName());

        AbroadJobSector sector = getSector(sectorId);

        String name = jobRole.getName().trim();

        if (repository.existsByNameIgnoreCaseAndSectorId(
                name,
                sectorId)) {

            throw new RuntimeException(
                    "Role already exists under this sector"
            );
        }

        jobRole.setId(null);
        jobRole.setName(name);
        jobRole.setSector(sector);

        if (jobRole.getActive() == null) {
            jobRole.setActive(true);
        }

        AbroadJobRole savedRole = repository.save(jobRole);

        return convertToDTO(savedRole);
    }

    @Override
    public List<AbroadJobRoleResponseDTO> getAll(
            String role,
            String email) {

        checkPermission(role, email, "GET");

        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<AbroadJobRoleResponseDTO> getBySector(
            Long sectorId,
            String role,
            String email) {

        checkPermission(role, email, "GET");

        getSector(sectorId);

        return repository
                .findBySectorIdAndActiveTrue(sectorId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public AbroadJobRoleResponseDTO getById(
            Long id,
            String role,
            String email) {

        checkPermission(role, email, "GET");

        AbroadJobRole jobRole =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Role not found with id: " + id
                                )
                        );

        return convertToDTO(jobRole);
    }

    @Override
    public AbroadJobRoleResponseDTO update(
            Long id,
            AbroadJobRole jobRole,
            Long sectorId,
            String role,
            String email) {

        checkPermission(role, email, "PUT");

        validateName(jobRole.getName());

        AbroadJobRole existing =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Role not found with id: "
                                                + id
                                )
                        );

        AbroadJobSector sector = getSector(sectorId);

        String name = jobRole.getName().trim();

        repository
                .findByNameIgnoreCaseAndSectorId(
                        name,
                        sectorId
                )
                .ifPresent(found -> {

                    if (!found.getId().equals(id)) {
                        throw new RuntimeException(
                                "Role already exists under this sector"
                        );
                    }
                });

        existing.setName(name);
        existing.setSector(sector);

        if (jobRole.getActive() != null) {
            existing.setActive(jobRole.getActive());
        }

        AbroadJobRole updatedRole =
                repository.save(existing);

        return convertToDTO(updatedRole);
    }

    @Override
    public void delete(
            Long id,
            String role,
            String email) {

        checkPermission(role, email, "DELETE");

        AbroadJobRole jobRole =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Role not found with id: "
                                                + id
                                )
                        );

        repository.delete(jobRole);
    }

    private AbroadJobSector getSector(
            Long sectorId) {

        if (sectorId == null) {
            throw new IllegalArgumentException(
                    "Sector id is required"
            );
        }

        return sectorRepository
                .findById(sectorId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sector not found with id: "
                                        + sectorId
                        )
                );
    }

    private void validateName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Role name is required"
            );
        }
    }

    private void checkPermission(
            String role,
            String email,
            String action) {

        if (!permissionService.hasPermission(
                role,
                email,
                action)) {

            throw new AccessDeniedException(
                    "No permission to perform " +
                            action +
                            " operation on Job Role"
            );
        }
    }

    private AbroadJobRoleResponseDTO convertToDTO(
            AbroadJobRole jobRole) {

        AbroadJobSector sector = jobRole.getSector();

        AbroadJobRoleResponseDTO.SectorResponseDTO
                sectorDTO =
                new AbroadJobRoleResponseDTO
                        .SectorResponseDTO(
                        sector.getId(),
                        sector.getName()
                );

        return new AbroadJobRoleResponseDTO(
                jobRole.getId(),
                jobRole.getName(),
                sectorDTO,
                jobRole.getActive()
        );
    }
}