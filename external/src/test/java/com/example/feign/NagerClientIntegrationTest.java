package com.example.feign;

import com.example.NagerCountryResponse;
import com.example.NagerHolidayResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest(classes = NagerClientIntegrationTest.TestConfig.class)
@ActiveProfiles("test")
class NagerClientIntegrationTest {

    @EnableAutoConfiguration
    @EnableFeignClients(clients = NagerClient.class)
    static class TestConfig {
    }

    @Autowired
    NagerClient nagerClient;

    @Test
    void testFindCountries() {
        List<NagerCountryResponse> countries = nagerClient.findCountries();

        assertNotNull(countries);
        assertFalse(countries.isEmpty());

        countries.forEach(System.out::println);
    }

    @Test
    void testFindHolidaysByYearAndCountryCode() {
        String countryCode = findTestableCountryCodeOrSkip();

        List<NagerHolidayResponse> holidays = nagerClient.findHolidaysByYearAndCountryCode(LocalDate.now().getYear(), countryCode);

        assertNotNull(holidays);
        assertFalse(holidays.isEmpty());

        holidays.forEach(System.out::println);
    }

    private String findTestableCountryCodeOrSkip() {
        List<NagerCountryResponse> countries = nagerClient.findCountries();
        assumeTrue(countries != null && !countries.isEmpty());

        return countries.stream()
                .map(NagerCountryResponse::countryCode)
                .findFirst()
                .orElseThrow();
    }
}
