package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadJobWorkType;
import com.abroad.Repository.AbroadJobWorkTypeRepository;
import com.abroad.Service.AbroadJobWorkTypeService;
import com.abroad.Service.PermissionService;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AbroadJobWorkTypeServiceImpl implements AbroadJobWorkTypeService {

    private final AbroadJobWorkTypeRepository repository;
    private final PermissionService permissionService;

    public AbroadJobWorkTypeServiceImpl(
            AbroadJobWorkTypeRepository repository,
            PermissionService permissionService) {
        this.repository = repository;
        this.permissionService = permissionService;
    }

    @Override
    public AbroadJobWorkType create(
            AbroadJobWorkType workType, String role, String email) {

        checkPermission(role, email, "POST");

        String name = validateName(workType.getName());

        if (repository.existsByNameIgnoreCase(name)) {
            throw new IllegalArgumentException(
                    "Work Type already exists: " + name);
        }

        AbroadJobWorkType newWorkType = new AbroadJobWorkType();
        newWorkType.setName(name);
        newWorkType.setActive(
                workType.getActive() == null ? true : workType.getActive());

        return repository.save(newWorkType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AbroadJobWorkType> getAll() {

        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public AbroadJobWorkType getById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Work Type not found with id: " + id));
    }

    @Override
    public AbroadJobWorkType update(
            Long id, AbroadJobWorkType workType, String role, String email) {

        checkPermission(role, email, "PUT");

        String name = validateName(workType.getName());

        AbroadJobWorkType existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Work Type not found with id: " + id));

        if (repository.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new IllegalArgumentException(
                    "Work Type already exists: " + name);
        }

        existing.setName(name);

        if (workType.getActive() != null) {
            existing.setActive(workType.getActive());
        }

        return repository.save(existing);
    }

    @Override
    public void delete(Long id, String role, String email) {

        checkPermission(role, email, "DELETE");

        AbroadJobWorkType existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Work Type not found with id: " + id));

        repository.delete(existing);
    }

    private String validateName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Work Type name is required");
        }

        return name.trim();
    }

    private void checkPermission(String role, String email, String action) {

        if (!permissionService.hasPermission(role, email, action)) {
            throw new AccessDeniedException(
                    "No permission to perform " + action
                            + " operation on Job Work Type");
        }
    }
}