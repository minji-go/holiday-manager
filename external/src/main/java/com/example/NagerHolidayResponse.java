package com.example;

import java.time.LocalDate;
import java.util.List;

public record NagerHolidayResponse(
        LocalDate date,
        String localName,
        String name,
        String countryCode,
        boolean fixed,
        boolean global,
        String counties,
        String launchYear,
        List<String> types) {
}
