package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadJobEducation;
import com.abroad.Repository.AbroadJobEducationRepository;
import com.abroad.Service.AbroadJobEducationService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbroadJobEducationServiceImpl
        implements AbroadJobEducationService {

    @Autowired
    private AbroadJobEducationRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadJobEducation create(
            AbroadJobEducation education,
            String role,
            String email) {

        checkPermission(role, email, "POST");

        if (education.getName() == null ||
                education.getName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Education name is required"
            );
        }

        String name = education.getName().trim();

        if (repository.existsByNameIgnoreCase(name)) {
            throw new RuntimeException(
                    "Education already exists: " + name
            );
        }

        education.setId(null);
        education.setName(name);

        if (education.getActive() == null) {
            education.setActive(true);
        }

        return repository.save(education);
    }

    @Override
    public List<AbroadJobEducation> getAll() {

        return repository.findAll();
    }

    @Override
    public AbroadJobEducation getById(Long id) {


        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Education not found with id: " + id
                        )
                );
    }

    @Override
    public AbroadJobEducation update(
            Long id,
            AbroadJobEducation education,
            String role,
            String email) {

        checkPermission(role, email, "PUT");

        AbroadJobEducation existing =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Education not found with id: "
                                                + id
                                )
                        );

        if (education.getName() == null ||
                education.getName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Education name is required"
            );
        }

        String name = education.getName().trim();

        repository.findByNameIgnoreCase(name)
                .ifPresent(found -> {
                    if (!found.getId().equals(id)) {
                        throw new RuntimeException(
                                "Education already exists: " + name
                        );
                    }
                });

        existing.setName(name);

        if (education.getActive() != null) {
            existing.setActive(education.getActive());
        }

        return repository.save(existing);
    }

    @Override
    public void delete(
            Long id,
            String role,
            String email) {

        checkPermission(role, email, "DELETE");

        AbroadJobEducation education =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Education not found with id: "
                                                + id
                                )
                        );

        repository.delete(education);
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
                            " operation on Job Education"
            );
        }
    }
}
