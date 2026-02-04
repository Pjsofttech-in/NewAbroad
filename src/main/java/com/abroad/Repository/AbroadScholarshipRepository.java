package com.abroad.Repository;

import com.abroad.Entity.AbroadScholarship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbroadScholarshipRepository extends JpaRepository<AbroadScholarship, Long> {
    List<AbroadScholarship> findByBranchCode(String branchCode);
}
