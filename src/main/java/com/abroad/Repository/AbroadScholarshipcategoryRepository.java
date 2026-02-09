package com.abroad.Repository;

import com.abroad.Entity.AbroadScholarshipcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbroadScholarshipcategoryRepository extends JpaRepository<AbroadScholarshipcategory, Long> {
    boolean existsByName(String name);
}
