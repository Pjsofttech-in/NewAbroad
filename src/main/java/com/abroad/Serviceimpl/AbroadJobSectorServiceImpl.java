
package com.abroad.Serviceimpl;

import com.abroad.DTO.AbroadJobSectorResponseDTO;
import com.abroad.Entity.AbroadJobEducation;
import com.abroad.Entity.AbroadJobSector;
import com.abroad.Repository.AbroadJobEducationRepository;
import com.abroad.Repository.AbroadJobSectorRepository;
import com.abroad.Service.AbroadJobSectorService;
import com.abroad.Service.PermissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AbroadJobSectorServiceImpl
        implements AbroadJobSectorService {

    @Autowired
    private AbroadJobSectorRepository repository;

    @Autowired
    private AbroadJobEducationRepository educationRepository;

    @Autowired
    private PermissionService permissionService;

    // CREATE SECTOR
    @Override
    public AbroadJobSectorResponseDTO create(
            AbroadJobSector sector,
            Long educationId,
            String role,
            String email) {

        checkPermission(role, email, "POST");

        validateName(sector.getName());

        AbroadJobEducation education = getEducation(educationId);

        String name = sector.getName().trim();

        if (repository.existsByNameIgnoreCaseAndEducationId(
                name, educationId)) {

            throw new RuntimeException(
                    "Sector already exists under this education");
        }

        sector.setId(null);
        sector.setName(name);
        sector.setEducation(education);

        if (sector.getActive() == null) {
            sector.setActive(true);
        }

        AbroadJobSector savedSector = repository.save(sector);

        return toResponse(savedSector);
    }

    // GET ALL SECTORS
    @Override
    @Transactional(readOnly = true)
    public List<AbroadJobSectorResponseDTO> getAll(
            String role,
            String email) {

        checkPermission(role, email, "GET");

        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // GET SECTORS BY EDUCATION
    @Override
    @Transactional(readOnly = true)
    public List<AbroadJobSectorResponseDTO> getByEducation(
            Long educationId,
            String role,
            String email) {

        checkPermission(role, email, "GET");

        getEducation(educationId);

        return repository
                .findByEducationIdAndActiveTrue(educationId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // GET SECTOR BY ID
    @Override
    @Transactional(readOnly = true)
    public AbroadJobSectorResponseDTO getById(
            Long id,
            String role,
            String email) {

        checkPermission(role, email, "GET");

        AbroadJobSector sector = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sector not found with id: " + id));

        return toResponse(sector);
    }

    // UPDATE SECTOR
    @Override
    public AbroadJobSectorResponseDTO update(
            Long id,
            AbroadJobSector sector,
            Long educationId,
            String role,
            String email) {

        checkPermission(role, email, "PUT");

        validateName(sector.getName());

        AbroadJobSector existing = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sector not found with id: " + id));

        AbroadJobEducation education = getEducation(educationId);

        String name = sector.getName().trim();

        repository.findByNameIgnoreCaseAndEducationId(
                name, educationId
        ).ifPresent(found -> {
            if (!found.getId().equals(id)) {
                throw new RuntimeException(
                        "Sector already exists under this education");
            }
        });

        existing.setName(name);
        existing.setEducation(education);

        if (sector.getActive() != null) {
            existing.setActive(sector.getActive());
        }

        AbroadJobSector updatedSector = repository.save(existing);

        return toResponse(updatedSector);
    }

    // DELETE SECTOR
    @Override
    public void delete(
            Long id,
            String role,
            String email) {

        checkPermission(role, email, "DELETE");

        AbroadJobSector sector = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sector not found with id: " + id));

        repository.delete(sector);
    }

    // GET EDUCATION
    private AbroadJobEducation getEducation(Long educationId) {

        if (educationId == null) {
            throw new IllegalArgumentException(
                    "Education id is required");
        }

        return educationRepository.findById(educationId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Education not found with id: "
                                        + educationId));
    }

    // VALIDATE SECTOR NAME
    private void validateName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Sector name is required");
        }
    }

    // MAP ENTITY TO DTO
    private AbroadJobSectorResponseDTO toResponse(
            AbroadJobSector sector) {

        AbroadJobEducation education = sector.getEducation();

        return new AbroadJobSectorResponseDTO(
                sector.getId(),
                sector.getName(),
                education != null ? education.getId() : null,
                education != null ? education.getName() : null,
                sector.getActive()
        );
    }

    // CHECK PERMISSION
    private void checkPermission(
            String role,
            String email,
            String action) {

        if (!permissionService.hasPermission(
                role, email, action)) {

            throw new AccessDeniedException(
                    "No permission to perform " +
                            action +
                            " operation on Job Sector");
        }
    }
}