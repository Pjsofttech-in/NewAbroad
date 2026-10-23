package com.abroad.Service;

import com.abroad.Entity.AbroadJobEducation;

import java.util.List;

public interface AbroadJobEducationService {

    AbroadJobEducation create(
            AbroadJobEducation education,
            String role,
            String email
    );

    List<AbroadJobEducation> getAll();

    AbroadJobEducation getById(Long id);

    AbroadJobEducation update(
            Long id,
            AbroadJobEducation education,
            String role,
            String email
    );

    void delete(
            Long id,
            String role,
            String email
    );
}