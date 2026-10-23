package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadJobCountry;
import com.abroad.Entity.AbroadJobLocation;
import com.abroad.Repository.AbroadJobCountryRepository;
import com.abroad.Repository.AbroadJobLocationRepository;
import com.abroad.Service.AbroadJobLocationService;
import com.abroad.Service.PermissionService;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AbroadJobLocationServiceImpl
        implements AbroadJobLocationService {

    private final AbroadJobLocationRepository repository;
    private final AbroadJobCountryRepository countryRepository;
    private final PermissionService permissionService;

    public AbroadJobLocationServiceImpl(
            AbroadJobLocationRepository repository,
            AbroadJobCountryRepository countryRepository,
            PermissionService permissionService) {

        this.repository = repository;
        this.countryRepository = countryRepository;
        this.permissionService = permissionService;
    }

    @Override
    public AbroadJobLocation create(
            AbroadJobLocation location,
            String role,
            String email) {

        checkPermission(role, email, "POST");

        String name = validateName(location.getName());

        if (location.getCountry() == null
                || location.getCountry().getId() == null) {
            throw new IllegalArgumentException(
                    "Country ID is required");
        }

        Long countryId = location.getCountry().getId();

        AbroadJobCountry country = countryRepository.findById(countryId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Country not found with id: " + countryId));

        if (!Boolean.TRUE.equals(country.getActive())) {
            throw new IllegalArgumentException(
                    "Cannot add a location to an inactive country");
        }

        if (repository.existsByNameIgnoreCaseAndCountry_Id(
                name, countryId)) {
            throw new IllegalArgumentException(
                    "Location already exists in this country: " + name);
        }

        AbroadJobLocation newLocation = new AbroadJobLocation();
        newLocation.setName(name);
        newLocation.setCountry(country);
        newLocation.setActive(
                location.getActive() == null
                        ? true : location.getActive());

        return repository.save(newLocation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AbroadJobLocation> getAll() {

        List<AbroadJobLocation> locations = repository.findAll();

        // Initialize country data before returning the entities.
        locations.forEach(location -> {
            if (location.getCountry() != null) {
                location.getCountry().getName();
            }
        });

        return locations;
    }

    @Override
    @Transactional(readOnly = true)
    public AbroadJobLocation getById(Long id) {


        AbroadJobLocation location = repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Location not found with id: " + id));

        if (location.getCountry() != null) {
            location.getCountry().getName();
        }

        return location;
    }

    @Override
    public AbroadJobLocation update(
            Long id,
            AbroadJobLocation location,
            String role,
            String email) {

        checkPermission(role, email, "PUT");

        String name = validateName(location.getName());

        AbroadJobLocation existing = repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Location not found with id: " + id));

        // Preserve the current country unless a new one is supplied.
        AbroadJobCountry country = existing.getCountry();

        if (location.getCountry() != null
                && location.getCountry().getId() != null) {

            Long countryId = location.getCountry().getId();

            country = countryRepository.findById(countryId)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Country not found with id: " + countryId));

            if (!Boolean.TRUE.equals(country.getActive())) {
                throw new IllegalArgumentException(
                        "Cannot assign a location to an inactive country");
            }
        }

        if (country == null || country.getId() == null) {
            throw new IllegalArgumentException(
                    "A country must be assigned to this location");
        }

        if (repository
                .existsByNameIgnoreCaseAndCountry_IdAndIdNot(
                        name, country.getId(), id)) {
            throw new IllegalArgumentException(
                    "Location already exists in this country: " + name);
        }

        existing.setName(name);
        existing.setCountry(country);

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

        AbroadJobLocation location = repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Location not found with id: " + id));

        repository.delete(location);
    }

    private String validateName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Location name is required");
        }

        return name.trim();
    }

    private void checkPermission(
            String role,
            String email,
            String action) {

        if (!permissionService.hasPermission(
                role, email, action)) {

            throw new AccessDeniedException(
                    "No permission to perform " + action
                            + " operation on Job Location");
        }
    }
}