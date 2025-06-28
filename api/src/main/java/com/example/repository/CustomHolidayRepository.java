package com.example.repository;

import com.example.dto.SearchHolidayDto;
import com.example.entity.Holiday;
import org.springframework.data.domain.Page;

public interface CustomHolidayRepository {
    Page<Holiday> search(SearchHolidayDto condition);
}
