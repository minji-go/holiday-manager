package com.example.entity;

import java.time.LocalDate;
import java.util.List;

public class HolidayTest {

    public static Holiday createHoliday(Country country, LocalDate date) {
        return new Holiday(country, date, "defaultLocalName", "defaultName", true, true, null, null, List.of());
    }
}
