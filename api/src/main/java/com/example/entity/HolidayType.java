package com.example.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HolidayType {
    private Long id;
    private Holiday holiday;
    private String type;

    public HolidayType(String type) {
        this.type = type;
    }
}
