package com.example.service;

import com.example.entity.Country;
import com.example.entity.Holiday;
import com.example.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayCommandService {
    private final HolidayRepository holidayRepository;

    public void deleteHoliday(Country country, int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        holidayRepository.deleteByCountryAndDateBetween(country, startDate, endDate);
    }

    public void saveHolidays(List<Holiday> holidays) {
        holidayRepository.saveAll(holidays);
    }

}
