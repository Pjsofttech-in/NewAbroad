package com.abroad.Repository;

import com.abroad.Entity.AbroadJobWorkType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbroadJobWorkTypeRepository
        extends JpaRepository<AbroadJobWorkType, Long> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    List<AbroadJobWorkType> findByActiveTrue();
}