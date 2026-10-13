package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadJobCurrency;
import com.abroad.Repository.AbroadJobCurrencyRepository;
import com.abroad.Service.AbroadJobCurrencyService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbroadJobCurrencyServiceImpl
        implements AbroadJobCurrencyService {

    @Autowired
    private AbroadJobCurrencyRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadJobCurrency create(
            AbroadJobCurrency currency,
            String role,
            String email) {

        checkPermission(role, email, "POST");

        validateName(currency.getName());

        String name = currency.getName().trim();

        if (repository.existsByNameIgnoreCase(name)) {
            throw new RuntimeException(
                    "Currency already exists: " + name
            );
        }

        currency.setId(null);
        currency.setName(name);

        if (currency.getActive() == null) {
            currency.setActive(true);
        }

        return repository.save(currency);
    }

    @Override
    public List<AbroadJobCurrency> getAll(
            String role,
            String email) {

        checkPermission(role, email, "GET");

        return repository.findAll();
    }

    @Override
    public AbroadJobCurrency getById(
            Long id,
            String role,
            String email) {

        checkPermission(role, email, "GET");

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Currency not found with id: " + id
                        )
                );
    }

    @Override
    public AbroadJobCurrency update(
            Long id,
            AbroadJobCurrency currency,
            String role,
            String email) {

        checkPermission(role, email, "PUT");

        validateName(currency.getName());

        AbroadJobCurrency existing =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Currency not found with id: "
                                                + id
                                )
                        );

        String name = currency.getName().trim();

        repository.findByNameIgnoreCase(name)
                .ifPresent(found -> {

                    if (!found.getId().equals(id)) {
                        throw new RuntimeException(
                                "Currency already exists: " + name
                        );
                    }
                });

        existing.setName(name);

        if (currency.getActive() != null) {
            existing.setActive(currency.getActive());
        }

        return repository.save(existing);
    }

    @Override
    public void delete(
            Long id,
            String role,
            String email) {

        checkPermission(role, email, "DELETE");

        AbroadJobCurrency currency =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Currency not found with id: "
                                                + id
                                )
                        );

        repository.delete(currency);
    }

    private void validateName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Currency name is required"
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
                            " operation on Job Currency"
            );
        }
    }
}