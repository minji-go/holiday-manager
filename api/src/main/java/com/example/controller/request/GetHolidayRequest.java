package com.example.controller.request;

import com.example.dto.SearchHolidayDto;

import java.time.LocalDate;

public record GetHolidayRequest(
        Integer year,
        String countryCode,
        LocalDate from,
        LocalDate to,
        int page,
        int size) {

    public SearchHolidayDto toDto() {
        return new SearchHolidayDto(year, countryCode, from, to, page, size);
    }
}
