package com.example.service;

import com.example.entity.Country;
import com.example.repository.HolidayRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HolidayCommandServiceTest {
    private HolidayRepository holidayRepository;
    private HolidayCommandService service;

    @BeforeEach
    void setUp() {
        holidayRepository = mock(HolidayRepository.class);
        service = new HolidayCommandService(holidayRepository);
    }

    @ParameterizedTest
    @ValueSource(ints = {1900, 2025})
    void refreshWhenNotExists(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        Country country = new Country("KR", "South Korea");

        service.deleteHoliday(country, year);

        verify(holidayRepository).deleteByCountryAndDateBetween(country, startDate, endDate);
    }

}
