package com.example.service;

import com.example.dto.CountryResponse;
import com.example.dto.HolidayResponse;
import com.example.entity.Country;
import com.example.entity.Holiday;
import com.example.repository.CountryRepository;
import com.example.repository.HolidayRepository;
import com.example.repository.HolidaySourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataInitializeService {
    private final CountryRepository countryRepository;
    private final HolidayRepository holidayRepository;
    private final HolidaySourceRepository holidaySourceRepository;

    @Transactional
    public List<Country> bulkInsertCountries() {
        log.info("Bulk inserting countries...");

        List<CountryResponse> responses = holidaySourceRepository.findAllCountries();
        List<Country> countries = responses.stream()
                .map(country -> new Country(country.code(), country.name()))
                .toList();

        countryRepository.saveAll(countries);
        log.info("Bulk inserted {} countries", countries.size());

        return countries;
    }

    @Transactional
    public void bulkInsertHolidays(Country country, int year) {
        log.info("Bulk inserting holidays...");

        List<HolidayResponse> responses = holidaySourceRepository.findHolidaysByYearAndCountryCode(year, country.getCode());
        List<Holiday> holidays = convertToHolidayEntity(country, responses);
        holidayRepository.saveAll(holidays);

        log.info("Bulk inserted {} holidays (country = {}, year = {})", holidays.size(), country.getCode(), year);
    }

    private List<Holiday> convertToHolidayEntity(Country country, List<HolidayResponse> responses) {
        return responses.stream()
                .map(holiday -> new Holiday(
                        country,
                        holiday.date(),
                        holiday.localName(),
                        holiday.name(),
                        holiday.fixed(),
                        holiday.global(),
                        holiday.counties(),
                        holiday.launchYear(),
                        holiday.types()))
                .toList();
    }
}
