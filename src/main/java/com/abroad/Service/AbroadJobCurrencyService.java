package com.abroad.Service;

import com.abroad.Entity.AbroadJobCurrency;

import java.util.List;

public interface AbroadJobCurrencyService {

    AbroadJobCurrency create(
            AbroadJobCurrency currency,
            String role,
            String email
    );

    List<AbroadJobCurrency> getAll(
            String role,
            String email
    );

    AbroadJobCurrency getById(
            Long id,
            String role,
            String email
    );

    AbroadJobCurrency update(
            Long id,
            AbroadJobCurrency currency,
            String role,
            String email
    );

    void delete(
            Long id,
            String role,
            String email
    );
}