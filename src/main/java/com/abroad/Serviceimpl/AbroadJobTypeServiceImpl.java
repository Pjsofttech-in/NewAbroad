package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadJobType;
import com.abroad.Repository.AbroadJobTypeRepository;
import com.abroad.Service.AbroadJobTypeService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbroadJobTypeServiceImpl
        implements AbroadJobTypeService {

    @Autowired
    private AbroadJobTypeRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadJobType create(
            AbroadJobType jobType,
            String role,
            String email) {

        checkPermission(role, email, "POST");

        validateName(jobType.getName());

        String name = jobType.getName().trim();

        if (repository.existsByNameIgnoreCase(name)) {
            throw new RuntimeException(
                    "Job Type already exists: " + name
            );
        }

        jobType.setId(null);
        jobType.setName(name);

        if (jobType.getActive() == null) {
            jobType.setActive(true);
        }

        return repository.save(jobType);
    }

    @Override
    public List<AbroadJobType> getAll(
            String role,
            String email) {

        checkPermission(role, email, "GET");

        return repository.findAll();
    }

    @Override
    public AbroadJobType getById(
            Long id,
            String role,
            String email) {

        checkPermission(role, email, "GET");

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Job Type not found with id: " + id
                        )
                );
    }

    @Override
    public AbroadJobType update(
            Long id,
            AbroadJobType jobType,
            String role,
            String email) {

        checkPermission(role, email, "PUT");

        validateName(jobType.getName());

        AbroadJobType existing =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job Type not found with id: "
                                                + id
                                )
                        );

        String name = jobType.getName().trim();

        repository.findByNameIgnoreCase(name)
                .ifPresent(found -> {

                    if (!found.getId().equals(id)) {
                        throw new RuntimeException(
                                "Job Type already exists: " + name
                        );
                    }
                });

        existing.setName(name);

        if (jobType.getActive() != null) {
            existing.setActive(jobType.getActive());
        }

        return repository.save(existing);
    }

    @Override
    public void delete(
            Long id,
            String role,
            String email) {

        checkPermission(role, email, "DELETE");

        AbroadJobType jobType =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job Type not found with id: "
                                                + id
                                )
                        );

        repository.delete(jobType);
    }

    private void validateName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Job Type name is required"
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
                            " operation on Job Type"
            );
        }
    }
}