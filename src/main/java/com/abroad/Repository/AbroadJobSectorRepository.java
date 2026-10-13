package com.abroad.Repository;

import com.abroad.Entity.AbroadJobSector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AbroadJobSectorRepository
        extends JpaRepository<AbroadJobSector, Long> {

    List<AbroadJobSector> findByEducationId(Long educationId);

    List<AbroadJobSector> findByEducationIdAndActiveTrue(Long educationId);

    Optional<AbroadJobSector> findByNameIgnoreCaseAndEducationId(
            String name,
            Long educationId
    );

    boolean existsByNameIgnoreCaseAndEducationId(
            String name,
            Long educationId
    );
}