package com.example.repository;

import com.example.entity.Country;
import com.example.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface HolidayRepository extends JpaRepository<Holiday, Long>, CustomHolidayRepository {

    boolean existsByCountryAndDateBetween(Country country, LocalDate startDate, LocalDate endDate);

    @Modifying
    @Query("DELETE Holiday h WHERE h.country = ?1 AND h.date BETWEEN ?2 AND ?3")
    void deleteByCountryAndDateBetween(Country country, LocalDate startDate, LocalDate endDate);
}
