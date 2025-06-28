package com.example.service;

import com.example.dto.HolidayResponse;
import com.example.entity.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.mockito.Mockito.*;

class HolidayApplicationServiceTest {
    private HolidayApplicationService service;
    private HolidaySourceQueryService sourceQueryService;
    private HolidayCommandService commandService;
    private HolidayQueryService queryService;

    @BeforeEach
    void setUp() {
        sourceQueryService = mock(HolidaySourceQueryService.class);
        commandService = mock(HolidayCommandService.class);
        queryService = mock(HolidayQueryService.class);
        service = new HolidayApplicationService(sourceQueryService, commandService, queryService);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void deleteIfExists(boolean exists) {
        int year = 2025;
        Country country = new Country("KR", "South Korea");

        when(queryService.findCountry(country.getCode())).thenReturn(country);
        when(queryService.existsHoliday(country, year)).thenReturn(exists);

        service.delete(year, country.getCode());

        verify(commandService, times(exists ? 1 : 0)).deleteHoliday(country, year);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void refreshIfNotExists(boolean hasData) {
        int year = 2025;
        Country country = new Country("KR", "South Korea");

        when(queryService.findCountry(country.getCode())).thenReturn(country);
        when(queryService.existsHoliday(country, year)).thenReturn(false);

        List<HolidayResponse> response = hasData ? List.of(mock(HolidayResponse.class)) : List.of();
        when(sourceQueryService.getHolidays(year, country.getCode())).thenReturn(response);

        service.refresh(year, country.getCode());

        verify(sourceQueryService).getHolidays(year, country.getCode());
        verify(commandService, times(hasData ? 1 : 0)).saveHolidays(any());
    }
}
