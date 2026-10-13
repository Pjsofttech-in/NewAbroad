package com.abroad.Service;

import com.abroad.Entity.AbroadJobLocation;

import java.util.List;

public interface AbroadJobLocationService {

    AbroadJobLocation create(
            AbroadJobLocation location,
            String role,
            String email
    );

    List<AbroadJobLocation> getAll(
            String role,
            String email
    );

    AbroadJobLocation getById(
            Long id,
            String role,
            String email
    );

    AbroadJobLocation update(
            Long id,
            AbroadJobLocation location,
            String role,
            String email
    );

    void delete(
            Long id,
            String role,
            String email
    );
}