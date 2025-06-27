package com.example.controller;

import com.example.service.HolidayApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(HolidayController.class)
class HolidayControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    HolidayApplicationService holidayApplicationService;

    @Test
    void testGetHolidays() throws Exception {
        when(holidayApplicationService.search(any())).thenReturn(mock());

        mockMvc.perform(get("/api/v1/holidays")
                        .param("year", "2025")
                        .param("countryCode", "KR")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk());

        verify(holidayApplicationService).search(argThat(
                req -> req.year() == 2025 && "KR".equals(req.countryCode())));
    }

    @Test
    void testRefreshHolidays() throws Exception {
        mockMvc.perform(put("/api/v1/holidays/2025/KR"))
                .andExpect(status().isOk());

        verify(holidayApplicationService).refresh(2025, "KR");
    }

    @Test
    void testDeleteHolidays() throws Exception {
        mockMvc.perform(delete("/api/v1/holidays/2025/KR"))
                .andExpect(status().isOk());

        verify(holidayApplicationService).delete(2025, "KR");
    }
}
