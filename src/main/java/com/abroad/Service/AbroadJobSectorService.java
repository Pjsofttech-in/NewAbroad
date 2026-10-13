package com.abroad.Service;

import com.abroad.Entity.AbroadJobSector;

import java.util.List;

public interface AbroadJobSectorService {

    AbroadJobSector create(
            AbroadJobSector sector,
            Long educationId,
            String role,
            String email
    );

    List<AbroadJobSector> getAll(
            String role,
            String email
    );

    List<AbroadJobSector> getByEducation(
            Long educationId,
            String role,
            String email
    );

    AbroadJobSector getById(
            Long id,
            String role,
            String email
    );

    AbroadJobSector update(
            Long id,
            AbroadJobSector sector,
            Long educationId,
            String role,
            String email
    );

    void delete(
            Long id,
            String role,
            String email
    );
}