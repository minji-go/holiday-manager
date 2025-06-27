package com.example.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeleteHolidayRequest(
        @NotNull
        Integer year,
        @NotBlank
        String countryCode) {
}
