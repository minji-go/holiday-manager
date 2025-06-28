package com.example.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "공휴일 정보 모델")
public record GetHolidayResponse(
        @Schema(description = "공휴일 날짜", example = "2025-01-01")
        LocalDate date,

        @Schema(description = "공휴일 현지 이름", example = "새해")
        String localName,

        @Schema(description = "공휴일 이름", example = "New Year's Day")
        String name,

        @Schema(description = "국가 코드", example = "KR")
        String countryCode,

        @Schema(description = "고정 여부", example = "false")
        boolean fixed,

        @Schema(description = "전 세계 공휴일 여부", example = "true")
        boolean global,

        @Schema(description = "카운티 목록", example = "[]")
        List<String> counties,

        @Schema(description = "출시 연도", example = "null")
        String launchYear,

        @Schema(description = "공휴일 유형 목록", example = "[\"Public\"]")
        List<String> types) {
}
