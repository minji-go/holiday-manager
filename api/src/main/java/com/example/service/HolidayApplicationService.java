package com.example.service;

import com.example.controller.response.GetHolidayResponse;
import com.example.controller.response.PageResult;
import com.example.dto.HolidayResponse;
import com.example.dto.SearchHolidayDto;
import com.example.entity.Country;
import com.example.entity.Holiday;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayApplicationService {
    private final HolidaySourceQueryService holidaySourceQueryService;
    private final HolidayCommandService holidayCommandService;
    private final HolidayQueryService holidayQueryService;

    @Transactional
    public PageResult<GetHolidayResponse> search(SearchHolidayDto dto) {
        Page<Holiday> result = holidayQueryService.findHolidays(dto);
        List<GetHolidayResponse> holidayResponses = convertToHolidayResponse(result.getContent());
        return new PageResult<>(dto.page(), dto.size(), result.getTotalElements(), holidayResponses);
    }

    private List<GetHolidayResponse> convertToHolidayResponse(List<Holiday> result) {
        return result.stream()
                .map(holiday -> new GetHolidayResponse(
                        holiday.getDate(),
                        holiday.getLocalName(),
                        holiday.getName(),
                        holiday.getCountry().getCode(),
                        holiday.isFixed(),
                        holiday.isGlobal(),
                        holiday.getCounties(),
                        holiday.getLaunchYear(),
                        holiday.getTypes()))
                .toList();
    }

    @Transactional
    public void delete(int year, String countryCode) {
        Country country = holidayQueryService.findCountry(countryCode);
        if (holidayQueryService.existsHoliday(country, year)) {
            holidayCommandService.deleteHoliday(country, year);
        }
    }

    @Transactional
    public void refresh(int year, String countryCode) {
        Country country = holidayQueryService.findCountry(countryCode);
        if (holidayQueryService.existsHoliday(country, year)) {
            holidayCommandService.deleteHoliday(country, year);
        }

        List<HolidayResponse> nextHolidays = holidaySourceQueryService.getHolidays(year, countryCode);
        if (!nextHolidays.isEmpty()) {
            holidayCommandService.saveHolidays(convertToEntity(country, nextHolidays));
        }
    }

    private List<Holiday> convertToEntity(Country country, List<HolidayResponse> sources) {
        return sources.stream()
                .map(source -> new Holiday(
                        country,
                        source.date(),
                        source.localName(),
                        source.name(),
                        source.fixed(),
                        source.global(),
                        source.counties(),
                        source.launchYear(),
                        source.types()))
                .toList();
    }
}
