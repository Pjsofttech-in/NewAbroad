package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadJobLocation;
import com.abroad.Repository.AbroadJobLocationRepository;
import com.abroad.Service.AbroadJobLocationService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbroadJobLocationServiceImpl
        implements AbroadJobLocationService {

    @Autowired
    private AbroadJobLocationRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadJobLocation create(
            AbroadJobLocation location,
            String role,
            String email) {

        checkPermission(role, email, "POST");

        validateName(location.getName());

        String name = location.getName().trim();

        if (repository.existsByNameIgnoreCase(name)) {
            throw new RuntimeException(
                    "Location already exists: " + name
            );
        }

        location.setId(null);
        location.setName(name);

        if (location.getActive() == null) {
            location.setActive(true);
        }

        return repository.save(location);
    }

    @Override
    public List<AbroadJobLocation> getAll(
            String role,
            String email) {

        checkPermission(role, email, "GET");

        return repository.findAll();
    }

    @Override
    public AbroadJobLocation getById(
            Long id,
            String role,
            String email) {

        checkPermission(role, email, "GET");

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Location not found with id: " + id
                        )
                );
    }

    @Override
    public AbroadJobLocation update(
            Long id,
            AbroadJobLocation location,
            String role,
            String email) {

        checkPermission(role, email, "PUT");

        validateName(location.getName());

        AbroadJobLocation existing =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Location not found with id: "
                                                + id
                                )
                        );

        String name = location.getName().trim();

        repository.findByNameIgnoreCase(name)
                .ifPresent(found -> {

                    if (!found.getId().equals(id)) {
                        throw new RuntimeException(
                                "Location already exists: " + name
                        );
                    }
                });

        existing.setName(name);

        if (location.getActive() != null) {
            existing.setActive(location.getActive());
        }

        return repository.save(existing);
    }

    @Override
    public void delete(
            Long id,
            String role,
            String email) {

        checkPermission(role, email, "DELETE");

        AbroadJobLocation location =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Location not found with id: "
                                                + id
                                )
                        );

        repository.delete(location);
    }

    private void validateName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Location name is required"
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
                            " operation on Job Location"
            );
        }
    }
}