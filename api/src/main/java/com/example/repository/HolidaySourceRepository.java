package com.example.repository;

import com.example.dto.CountryResponse;
import com.example.dto.HolidayResponse;

import java.util.List;

public interface HolidaySourceRepository {
    List<CountryResponse> findAllCountries();

    List<HolidayResponse> findHolidaysByYearAndCountryCode(int year, String countryCode);
}
