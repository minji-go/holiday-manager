package com.example.repository;

import com.example.dto.HolidayResponse;

import java.util.List;

public interface HolidaySourceRepository {
    List<HolidayResponse> findHolidaysByYearAndCountryCode(int year, String countryCode);
}
