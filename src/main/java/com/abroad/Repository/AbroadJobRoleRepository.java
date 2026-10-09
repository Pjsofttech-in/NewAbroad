package com.abroad.Repository;

import com.abroad.Entity.AbroadJobRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AbroadJobRoleRepository
        extends JpaRepository<AbroadJobRole, Long> {

    List<AbroadJobRole> findBySectorId(Long sectorId);

    List<AbroadJobRole> findBySectorIdAndActiveTrue(Long sectorId);

    Optional<AbroadJobRole> findByNameIgnoreCaseAndSectorId(
            String name,
            Long sectorId
    );

    boolean existsByNameIgnoreCaseAndSectorId(
            String name,
            Long sectorId
    );
}
