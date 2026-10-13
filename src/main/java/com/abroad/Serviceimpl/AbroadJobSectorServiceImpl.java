package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadJobEducation;
import com.abroad.Entity.AbroadJobSector;
import com.abroad.Repository.AbroadJobEducationRepository;
import com.abroad.Repository.AbroadJobSectorRepository;
import com.abroad.Service.AbroadJobSectorService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbroadJobSectorServiceImpl
        implements AbroadJobSectorService {

    @Autowired
    private AbroadJobSectorRepository repository;

    @Autowired
    private AbroadJobEducationRepository educationRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadJobSector create(
            AbroadJobSector sector,
            Long educationId,
            String role,
            String email) {

        checkPermission(role, email, "POST");

        validateName(sector.getName());

        AbroadJobEducation education =
                getEducation(educationId);

        String name = sector.getName().trim();

        if (repository.existsByNameIgnoreCaseAndEducationId(
                name,
                educationId)) {

            throw new RuntimeException(
                    "Sector already exists under this education"
            );
        }

        sector.setId(null);
        sector.setName(name);
        sector.setEducation(education);

        if (sector.getActive() == null) {
            sector.setActive(true);
        }

        return repository.save(sector);
    }

    @Override
    public List<AbroadJobSector> getAll(
            String role,
            String email) {

        checkPermission(role, email, "GET");

        return repository.findAll();
    }

    @Override
    public List<AbroadJobSector> getByEducation(
            Long educationId,
            String role,
            String email) {

        checkPermission(role, email, "GET");

        getEducation(educationId);

        return repository
                .findByEducationIdAndActiveTrue(educationId);
    }

    @Override
    public AbroadJobSector getById(
            Long id,
            String role,
            String email) {

        checkPermission(role, email, "GET");

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sector not found with id: " + id
                        )
                );
    }

    @Override
    public AbroadJobSector update(
            Long id,
            AbroadJobSector sector,
            Long educationId,
            String role,
            String email) {

        checkPermission(role, email, "PUT");

        validateName(sector.getName());

        AbroadJobSector existing =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Sector not found with id: "
                                                + id
                                )
                        );

        AbroadJobEducation education =
                getEducation(educationId);

        String name = sector.getName().trim();

        repository
                .findByNameIgnoreCaseAndEducationId(
                        name,
                        educationId
                )
                .ifPresent(found -> {

                    if (!found.getId().equals(id)) {
                        throw new RuntimeException(
                                "Sector already exists under this education"
                        );
                    }
                });

        existing.setName(name);
        existing.setEducation(education);

        if (sector.getActive() != null) {
            existing.setActive(sector.getActive());
        }

        return repository.save(existing);
    }

    @Override
    public void delete(
            Long id,
            String role,
            String email) {

        checkPermission(role, email, "DELETE");

        AbroadJobSector sector =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Sector not found with id: "
                                                + id
                                )
                        );

        repository.delete(sector);
    }

    private AbroadJobEducation getEducation(
            Long educationId) {

        if (educationId == null) {
            throw new IllegalArgumentException(
                    "Education id is required"
            );
        }

        return educationRepository
                .findById(educationId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Education not found with id: "
                                        + educationId
                        )
                );
    }

    private void validateName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Sector name is required"
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
                            " operation on Job Sector"
            );
        }
    }
}