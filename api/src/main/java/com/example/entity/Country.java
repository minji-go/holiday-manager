package com.example.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Country {
    private String code;
    private String name;

    public Country(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
