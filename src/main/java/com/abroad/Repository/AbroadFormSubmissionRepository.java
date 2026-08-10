package com.abroad.Repository;

import com.abroad.Entity.AbroadFormSubmission;
import com.abroad.Enum.FormType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbroadFormSubmissionRepository
        extends JpaRepository<AbroadFormSubmission, Long> {

    List<AbroadFormSubmission> findByFormType(FormType formType);
}
