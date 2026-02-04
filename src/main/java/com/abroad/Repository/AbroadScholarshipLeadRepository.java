package com.abroad.Repository;

import com.abroad.Entity.AbroadScholarshipLead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbroadScholarshipLeadRepository extends JpaRepository<AbroadScholarshipLead, Long> {


    List<AbroadScholarshipLead> findByScholarship(String scholarship);
}
