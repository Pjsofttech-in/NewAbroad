package com.abroad.Service;

import com.abroad.Entity.AbroadJobCountry;
import java.util.List;

public interface AbroadJobCountryService {

    AbroadJobCountry createCountry(
            AbroadJobCountry country, String role, String email);

    List<AbroadJobCountry> getAllCountries();

    AbroadJobCountry getCountryById(Long id);

    AbroadJobCountry updateCountry(
            Long id, AbroadJobCountry country, String role, String email);

    void deleteCountry(Long id, String role, String email);
}