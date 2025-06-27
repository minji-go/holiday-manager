package com.example.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Holiday extends BaseEntity {
    private Country country;
    private LocalDate date;
    private String localName;
    private String name;
    private boolean isFixed;
    private boolean isGlobal;
    private String counties;
    private String launchYear;
    private List<HolidayType> types = new ArrayList<>();

    public Holiday(Country country, LocalDate date, String localName, String name, boolean isFixed, boolean isGlobal, String counties, String launchYear, List<HolidayType> types) {
        this.country = country;
        this.date = date;
        this.localName = localName;
        this.name = name;
        this.isFixed = isFixed;
        this.isGlobal = isGlobal;
        this.counties = counties;
        this.launchYear = launchYear;
        this.types = types;
    }
}
