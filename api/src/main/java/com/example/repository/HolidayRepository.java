package com.example.repository;

import com.example.dto.SearchHolidayDto;
import com.example.entity.Country;
import com.example.entity.Holiday;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository {

    boolean existsByCountryAndDateBetween(Country country, LocalDate startDate, LocalDate endDate);

    void deleteByCountryAndDateBetween(Country country, LocalDate startDate, LocalDate endDate);

    void saveAll(List<Holiday> holidays);

    Page<Holiday> search(SearchHolidayDto dto);
}
