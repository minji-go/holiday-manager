package com.example.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "holiday")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Holiday extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "country_code")
    private Country country;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "local_name")
    private String localName;
    @Column(name = "name")
    private String name;
    @Column(name = "is_fixed")
    private boolean isFixed;
    @Column(name = "is_global")
    private boolean isGlobal;
    @ElementCollection
    @CollectionTable(name = "country_region")
    @Column(name = "region")
    private List<String> counties = new ArrayList<>();
    @Column(name = "launch_year")
    private String launchYear;
    @ElementCollection
    @CollectionTable(name = "holiday_type")
    @Column(name = "type")
    private List<String> types = new ArrayList<>();

    public Holiday(Country country, LocalDate date, String localName, String name, boolean isFixed, boolean isGlobal, List<String> counties, String launchYear, List<String> types) {
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

