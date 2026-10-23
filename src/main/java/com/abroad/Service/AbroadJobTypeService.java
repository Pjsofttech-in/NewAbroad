package com.abroad.Service;

import com.abroad.Entity.AbroadJobType;

import java.util.List;

public interface AbroadJobTypeService {

    AbroadJobType create(
            AbroadJobType jobType,
            String role,
            String email
    );

    List<AbroadJobType> getAll();

    AbroadJobType getById(Long id);

    AbroadJobType update(
            Long id,
            AbroadJobType jobType,
            String role,
            String email
    );

    void delete(
            Long id,
            String role,
            String email
    );
}