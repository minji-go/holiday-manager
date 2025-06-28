package com.example.config;

import com.example.entity.Country;
import com.example.service.DataInitializeService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final DataInitializeService dataInitializeService;
    private final Executor ioExecutor;
    private final int INIT_YEAR_RANGE = 5;

    @PostConstruct
    public void initializeData() {
        log.info("Data initialization starting...");
        bulkInsertHolidaysFor5Years(dataInitializeService.bulkInsertCountries());
        log.info("Data initialization completed successfully");
    }

    public void bulkInsertHolidaysFor5Years(List<Country> countries) {
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        int thisYear = LocalDate.now().getYear();

        for (Country country : countries) {
            for (int i = INIT_YEAR_RANGE; i > 0; i--) {
                int year = thisYear - i + 1;
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> dataInitializeService.bulkInsertHolidays(country, year), ioExecutor);
                futures.add(future);
            }
        }

        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } catch (CompletionException e) {
            log.error("Error during data initialization", e);
            throw new RuntimeException("Data initialization", e.getCause());
        }
    }

}
