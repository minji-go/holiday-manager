package com.example.config;

import com.example.dto.SearchHolidayDto;
import com.example.entity.Country;
import com.example.entity.Holiday;
import com.example.repository.CountryRepository;
import com.example.repository.HolidayRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Profile("local")
@Configuration
public class DevMockConfig {
    @Bean
    public HolidayRepository holidayRepository() {
        return new HolidayRepository() {
            @Override
            public boolean existsByCountryAndDateBetween(Country country, LocalDate startDate, LocalDate endDate) {
                return false;
            }

            @Override
            public void deleteByCountryAndDateBetween(Country country, LocalDate startDate, LocalDate endDate) {

            }

            @Override
            public void saveAll(List<Holiday> holidays) {

            }

            @Override
            public Page<Holiday> search(SearchHolidayDto dto) {
                return null;
            }
        };
    }

    @Bean
    public CountryRepository countryRepository() {
        return new CountryRepository() {
            @Override
            public Optional<Country> findByCode(String code) {
                return Optional.empty();
            }
        };
    }
}
