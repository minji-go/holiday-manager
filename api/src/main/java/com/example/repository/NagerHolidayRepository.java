package com.example.repository;

import com.example.NagerHolidayResponse;
import com.example.dto.HolidayResponse;
import com.example.feign.NagerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NagerHolidayRepository implements HolidaySourceRepository {
    private final NagerClient nagerClient;

    @Override
    public List<HolidayResponse> findHolidaysByYearAndCountryCode(int year, String countryCode) {
        List<NagerHolidayResponse> holidays = nagerClient.findHolidaysByYearAndCountryCode(year, countryCode);
        return convertToResponse(holidays);
    }

    private List<HolidayResponse> convertToResponse(List<NagerHolidayResponse> holidays) {
        return holidays.stream()
                .map(holiday -> new HolidayResponse(
                        holiday.date(),
                        holiday.localName(),
                        holiday.name(),
                        holiday.countryCode(),
                        holiday.fixed(),
                        holiday.global(),
                        holiday.counties(),
                        holiday.launchYear(),
                        holiday.types()))
                .toList();
    }
}
