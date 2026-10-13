package com.abroad.Repository;

import com.abroad.Entity.AbroadJobEducation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AbroadJobEducationRepository
        extends JpaRepository<AbroadJobEducation, Long> {

    Optional<AbroadJobEducation> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    List<AbroadJobEducation> findByActiveTrue();
}