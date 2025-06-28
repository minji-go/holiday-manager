package com.example.repository;

import com.example.config.QueryDslConfig;
import com.example.entity.Country;
import com.example.entity.Holiday;
import com.example.entity.HolidayTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Import(QueryDslConfig.class)
@DataJpaTest
@Transactional
class HolidayRepositoryTest {

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Test
    void returnsTrueIfExists() {
        Country country = new Country("KR", "South Korea");
        Holiday holiday = HolidayTest.createHoliday(country, LocalDate.of(2025, 1, 1));
        countryRepository.save(country);
        holidayRepository.save(holiday);

        boolean isExists = holidayRepository.existsByCountryAndDateBetween(country,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31));

        assertTrue(isExists);
    }

    @Test
    void returnsFalseIfNotExists() {
        Country country = countryRepository.save(new Country("KR", "South Korea"));

        boolean isExists = holidayRepository.existsByCountryAndDateBetween(country,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31));

        assertFalse(isExists);
    }

    @Test
    void deleteByCountryAndDateBetween_shouldDeleteCorrectly() {
        Country kr = new Country("KR", "South Korea");
        Country us = new Country("US", "United States");
        countryRepository.saveAll(List.of(kr, us));

        Holiday h1 = HolidayTest.createHoliday(kr, LocalDate.of(2025, 1, 1));
        Holiday h2 = HolidayTest.createHoliday(kr, LocalDate.of(2026, 3, 1));
        Holiday h3 = HolidayTest.createHoliday(us, LocalDate.of(2025, 7, 4));
        holidayRepository.saveAll(List.of(h1, h2, h3));

        holidayRepository.deleteByCountryAndDateBetween(kr,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31));

        List<Holiday> remaining = holidayRepository.findAll();
        assertThat(remaining).hasSize(2);
    }

}
