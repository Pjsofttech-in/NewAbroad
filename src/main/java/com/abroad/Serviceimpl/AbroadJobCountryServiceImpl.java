package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadJobCountry;
import com.abroad.Repository.AbroadJobCountryRepository;
import com.abroad.Service.AbroadJobCountryService;
import com.abroad.Service.PermissionService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AbroadJobCountryServiceImpl
        implements AbroadJobCountryService {

    private final AbroadJobCountryRepository countryRepository;
    private final PermissionService permissionService;

    public AbroadJobCountryServiceImpl(
            AbroadJobCountryRepository countryRepository,
            PermissionService permissionService) {
        this.countryRepository = countryRepository;
        this.permissionService = permissionService;
    }

    private void checkPermission(
            String role, String email, String action) {

        if (!permissionService.hasPermission(role, email, action)) {
            throw new AccessDeniedException(
                    "No permission to " + action
                            + " Job Country"
            );
        }
    }

    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Country name is required");
        }

        return name.trim();
    }

    @Override
    public AbroadJobCountry createCountry(
            AbroadJobCountry country, String role, String email) {

        checkPermission(role, email, "POST");

        String name = validateName(country.getName());

        if (countryRepository.existsByNameIgnoreCase(name)) {
            throw new IllegalArgumentException(
                    "Country already exists: " + name);
        }

        AbroadJobCountry newCountry = new AbroadJobCountry();
        newCountry.setName(name);
        newCountry.setActive(
                country.getActive() == null
                        ? true : country.getActive());

        return countryRepository.save(newCountry);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AbroadJobCountry> getAllCountries(
            String role, String email) {

        checkPermission(role, email, "GET");

        return countryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public AbroadJobCountry getCountryById(
            Long id, String role, String email) {

        checkPermission(role, email, "GET");

        return countryRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Country not found with id: " + id));
    }

    @Override
    public AbroadJobCountry updateCountry(
            Long id, AbroadJobCountry country,
            String role, String email) {

        checkPermission(role, email, "PUT");

        AbroadJobCountry existingCountry =
                countryRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Country not found with id: " + id));

        String name = validateName(country.getName());

        if (countryRepository
                .existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new IllegalArgumentException(
                    "Country already exists: " + name);
        }

        existingCountry.setName(name);

        if (country.getActive() != null) {
            existingCountry.setActive(country.getActive());
        }

        return countryRepository.save(existingCountry);
    }

    @Override
    public void deleteCountry(
            Long id, String role, String email) {

        checkPermission(role, email, "DELETE");

        AbroadJobCountry country = countryRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Country not found with id: " + id));

        countryRepository.delete(country);
    }
}