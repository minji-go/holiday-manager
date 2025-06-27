package com.example.service;

import com.example.ApiException;
import com.example.ErrorType;
import com.example.dto.SearchHolidayDto;
import com.example.entity.Country;
import com.example.entity.Holiday;
import com.example.repository.CountryRepository;
import com.example.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HolidayQueryService {
    private final HolidayRepository holidayRepository;
    private final CountryRepository countryRepository;

    public boolean existsHoliday(Country country, int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return holidayRepository.existsByCountryAndDateBetween(country, startDate, endDate);
    }

    public Country findCountry(String countryCode) {
        return countryRepository.findByCode(countryCode)
                .orElseThrow(() -> new ApiException(ErrorType.NO_RESOURCE.getDescription(), ErrorType.NO_RESOURCE, HttpStatus.BAD_REQUEST));
    }

    public Page<Holiday> findHolidays(SearchHolidayDto dto) {
        return holidayRepository.search(dto);
    }
}
