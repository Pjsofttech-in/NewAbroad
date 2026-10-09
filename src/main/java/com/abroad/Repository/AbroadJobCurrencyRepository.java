package com.abroad.Repository;

import com.abroad.Entity.AbroadJobCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AbroadJobCurrencyRepository
        extends JpaRepository<AbroadJobCurrency, Long> {

    Optional<AbroadJobCurrency> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    List<AbroadJobCurrency> findByActiveTrue();
}