package com.abroad.Repository;

import com.abroad.Entity.AbroadJobApplication;
import com.abroad.Enum.JobApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface AbroadJobApplicationRepository
        extends JpaRepository<AbroadJobApplication, Long> {

    boolean existsByJobIdAndEmailIgnoreCase(
            Long jobId,
            String email
    );

    List<AbroadJobApplication> findByJobId(Long jobId);

    List<AbroadJobApplication> findByEmailIgnoreCase(String email);

    List<AbroadJobApplication> findByStatus(
            JobApplicationStatus status
    );

    List<AbroadJobApplication> findByActiveTrue();


    @Query("""
        SELECT a
        FROM AbroadJobApplication a
        WHERE a.active = true
          AND (:jobId IS NULL OR a.job.id = :jobId)
          AND (:status IS NULL OR a.status = :status)
          AND (
              :applicantEmail IS NULL
              OR LOWER(a.email) LIKE LOWER(CONCAT('%', :applicantEmail, '%'))
          )
        """)
    Page<AbroadJobApplication> searchApplications(
            @Param("jobId") Long jobId,
            @Param("status") JobApplicationStatus status,
            @Param("applicantEmail") String applicantEmail,
            Pageable pageable
    );
}