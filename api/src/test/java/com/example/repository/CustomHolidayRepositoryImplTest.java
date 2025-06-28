package com.example.repository;

import com.example.config.QueryDslConfig;
import com.example.dto.SearchHolidayDto;
import com.example.entity.Country;
import com.example.entity.Holiday;
import com.example.entity.HolidayTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Import(QueryDslConfig.class)
@DataJpaTest
@Transactional
class CustomHolidayRepositoryImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomHolidayRepositoryImpl customHolidayRepository;

    private Country kr;
    private Country us;
    private Holiday kr2024Holiday;
    private Holiday kr2025Holiday;
    private Holiday us2025Holiday;
    private Holiday kr2025Holiday2;

    @BeforeEach
    void setUp() {
        kr = new Country("KR", "South Korea");
        us = new Country("US", "United States");
        entityManager.persist(kr);
        entityManager.persist(us);

        kr2024Holiday = HolidayTest.createHoliday(kr, LocalDate.of(2024, 1, 1));
        kr2025Holiday = HolidayTest.createHoliday(kr, LocalDate.of(2025, 1, 1));
        us2025Holiday = HolidayTest.createHoliday(us, LocalDate.of(2025, 7, 4));
        kr2025Holiday2 = HolidayTest.createHoliday(kr, LocalDate.of(2025, 12, 25));
        entityManager.persist(kr2025Holiday);
        entityManager.persist(us2025Holiday);
        entityManager.persist(kr2024Holiday);
        entityManager.persist(kr2025Holiday2);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void searchWithoutConditions() {
        SearchHolidayDto dto = new SearchHolidayDto(null, null, null, null, 2, 2);

        Page<Holiday> result = customHolidayRepository.search(dto);

        assertThat(result.getTotalElements()).isEqualTo(4);
        assertThat(result.getContent()).containsExactly(us2025Holiday, kr2025Holiday2);
    }

    @Test
    void searchByYear() {
        SearchHolidayDto dto = new SearchHolidayDto(2024, null, null, null, 1, 10);

        Page<Holiday> result = customHolidayRepository.search(dto);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent())
                .extracting(Holiday::getDate)
                .allMatch(date -> date.getYear() == 2024);
    }

    @Test
    void searchByCountryCode() {
        SearchHolidayDto dto = new SearchHolidayDto(
                null, "KR", null, null, 1, 2
        );

        Page<Holiday> result = customHolidayRepository.search(dto);

        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getContent())
                .extracting(Holiday::getCountry)
                .allMatch(country -> kr.equals(country));
    }

    @Test
    void searchByYearAndCountryCode() {
        SearchHolidayDto dto = new SearchHolidayDto(2025, "KR", null, null, 2, 1);

        Page<Holiday> result = customHolidayRepository.search(dto);

        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).containsExactly(kr2025Holiday2);
    }

    @Test
    void searchByDateRange() {
        SearchHolidayDto dto = new SearchHolidayDto(null, null,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 7, 4),
                1, 2
        );

        Page<Holiday> result = customHolidayRepository.search(dto);

        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).contains(kr2025Holiday, us2025Holiday);
    }

}
