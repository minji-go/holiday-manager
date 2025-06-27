package com.example.controller;

import com.example.controller.request.GetHolidayRequest;
import com.example.controller.response.GetHolidayResponse;
import com.example.controller.response.PageResult;
import com.example.service.HolidayApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/holidays")
@RequiredArgsConstructor
public class HolidayController {
    private final HolidayApplicationService holidayApplicationService;

    @GetMapping
    public ResponseEntity<PageResult<GetHolidayResponse>> getHolidays(@Valid @ModelAttribute GetHolidayRequest request) {
        PageResult<GetHolidayResponse> result = holidayApplicationService.search(request.toDto());
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{year}/{countryCode}")
    public ResponseEntity<Void> refreshHolidays(@PathVariable int year,
                                                @PathVariable String countryCode) {
        holidayApplicationService.refresh(year, countryCode);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{year}/{countryCode}")
    public ResponseEntity<Void> deleteHolidays(@PathVariable int year,
                                               @PathVariable String countryCode) {
        holidayApplicationService.delete(year, countryCode);
        return ResponseEntity.ok().build();
    }

}


