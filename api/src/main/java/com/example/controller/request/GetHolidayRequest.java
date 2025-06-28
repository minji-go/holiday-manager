package com.example.controller.request;

import com.example.dto.SearchHolidayDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "공휴일 검색 요청을 위한 모델")
public record GetHolidayRequest(
        @Schema(description = "연도", example = "2025", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        Integer year,

        @Schema(description = "국가 코드", example = "KR", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String countryCode,

        @Schema(description = "시작일", example = "2025-01-01", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        LocalDate from,

        @Schema(description = "종료일", example = "2025-03-31", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        LocalDate to,

        @NotNull(message = "페이지 번호는 필수입니다.")
        @Min(value = 1, message = "페이지 번호는 1이상이어야 합니다.")
        @Schema(description = "페이지 번호", example = "1", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 1)
        Integer page,

        @NotNull(message = "페이지 크기는 필수입니다.")
        @Min(value = 1, message = "페이지크기는 1이상이어야 합니다.")
        @Max(value = 100, message = "페이지크기는 100이하여야 합니다.")
        @Schema(description = "페이지 사이즈", example = "10", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 1, maxLength = 100)
        Integer size) {

    public SearchHolidayDto toDto() {
        return new SearchHolidayDto(year, countryCode, from, to, page, size);
    }
}
