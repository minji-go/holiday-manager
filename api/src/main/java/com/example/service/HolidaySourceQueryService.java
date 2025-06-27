package com.example.service;

import com.example.dto.HolidayResponse;
import com.example.repository.HolidaySourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidaySourceQueryService {
    private final HolidaySourceRepository holidaySourceRepository;

    public List<HolidayResponse> getHolidays(int year, String countryCode) {
        return holidaySourceRepository.findHolidaysByYearAndCountryCode(year, countryCode);
    }
}
