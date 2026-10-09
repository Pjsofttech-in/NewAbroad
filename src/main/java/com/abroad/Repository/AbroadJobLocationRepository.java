package com.abroad.Repository;

import com.abroad.Entity.AbroadJobLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AbroadJobLocationRepository
        extends JpaRepository<AbroadJobLocation, Long> {

    Optional<AbroadJobLocation> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    List<AbroadJobLocation> findByActiveTrue();
}