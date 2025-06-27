package com.example.repository;

import com.example.entity.Country;

import java.util.Optional;

public interface CountryRepository {
    Optional<Country> findByCode(String code);
}
