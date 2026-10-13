package com.abroad.Repository;

import com.abroad.Entity.AbroadJobType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AbroadJobTypeRepository
        extends JpaRepository<AbroadJobType, Long> {

    Optional<AbroadJobType> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    List<AbroadJobType> findByActiveTrue();
}