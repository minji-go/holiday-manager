package com.example.repository;

import com.example.NagerCountryResponse;
import com.example.NagerHolidayResponse;
import com.example.dto.CountryResponse;
import com.example.dto.HolidayResponse;
import com.example.feign.NagerClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.*;

class NagerHolidayRepositoryTest {
    private NagerClient nagerClient;
    private NagerHolidayRepository repository;

    @BeforeEach
    void setUp() {
        nagerClient = mock(NagerClient.class);
        repository = new NagerHolidayRepository(nagerClient);
    }

    @Test
    void findAllCountries() {
        when(nagerClient.findCountries())
                .thenReturn(List.of(new NagerCountryResponse("KR", "South Korea"),
                        new NagerCountryResponse("US", "United States")));

        List<CountryResponse> result = repository.findAllCountries();

        assertThat(result)
                .extracting(CountryResponse::code, CountryResponse::name)
                .containsExactlyInAnyOrder(
                        tuple("KR", "South Korea"),
                        tuple("US", "United States")
                );
    }

    @Test
    void findHolidaysByYearAndCountryCode() {
        int year = 2025;
        String countryCode = "KR";

        when(nagerClient.findHolidaysByYearAndCountryCode(year, countryCode))
                .thenReturn(createSampleNagerApiResponse(year, countryCode));

        List<HolidayResponse> result = repository.findHolidaysByYearAndCountryCode(year, countryCode);

        verify(nagerClient).findHolidaysByYearAndCountryCode(year, countryCode);

        assertThat(result)
                .extracting(HolidayResponse::date, HolidayResponse::countryCode)
                .containsExactly(tuple(LocalDate.of(year, 1, 1), countryCode));

    }

    private List<NagerHolidayResponse> createSampleNagerApiResponse(int year, String countryCode) {
        return List.of(new NagerHolidayResponse(
                LocalDate.of(year, 1, 1), null, null, countryCode, true, true, null, null, List.of())
        );
    }
}
