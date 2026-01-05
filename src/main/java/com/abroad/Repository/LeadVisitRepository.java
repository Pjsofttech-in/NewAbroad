package com.abroad.Repository;

import com.abroad.Entity.AbroadLeadVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadVisitRepository extends JpaRepository<AbroadLeadVisit,Long> {
}
