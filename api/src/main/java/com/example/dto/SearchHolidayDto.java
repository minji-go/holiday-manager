package com.example.dto;

import java.time.LocalDate;

public record SearchHolidayDto(
        Integer year,
        String countryCode,
        LocalDate from,
        LocalDate to,
        int page,
        int size) {
}
