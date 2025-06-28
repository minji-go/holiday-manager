package com.example.controller;

import com.example.controller.request.GetHolidayRequest;
import com.example.controller.response.ErrorResponse;
import com.example.controller.response.GetHolidayResponse;
import com.example.controller.response.PageResult;
import com.example.service.HolidayApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/holidays")
@RequiredArgsConstructor
@Tag(name = "holiday", description = "holiday api")
public class HolidayController {
    private final HolidayApplicationService holidayApplicationService;

    @Operation(summary = "Search Holiday API", description = "공휴일 검색결과 제공", tags = {"holiday"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = PageResult.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping
    public ResponseEntity<PageResult<GetHolidayResponse>> getHolidays(@Valid @ModelAttribute GetHolidayRequest request) {
        PageResult<GetHolidayResponse> result = holidayApplicationService.search(request.toDto());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Refresh Holiday API", description = "공휴일 데이터 갱신", tags = {"holiday"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PutMapping("/{year}/{countryCode}")
    public ResponseEntity<Void> refreshHolidays(@PathVariable int year,
                                                @PathVariable String countryCode) {
        holidayApplicationService.refresh(year, countryCode);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Delete Holiday API", description = "공휴일 데이터 삭제", tags = {"holiday"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping("/{year}/{countryCode}")
    public ResponseEntity<Void> deleteHolidays(@PathVariable int year,
                                               @PathVariable String countryCode) {
        holidayApplicationService.delete(year, countryCode);
        return ResponseEntity.ok().build();
    }

}


