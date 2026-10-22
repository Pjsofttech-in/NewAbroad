package com.abroad.Repository;

import com.abroad.Entity.AbroadJobCountry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbroadJobCountryRepository
        extends JpaRepository<AbroadJobCountry, Long> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    List<AbroadJobCountry> findByActiveTrue();
}

