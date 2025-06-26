package com.example.feign;

import com.example.NagerCountryResponse;
import com.example.NagerHolidayResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "nagerClient", url = "${external.nager.url}")
public interface NagerClient {

    @GetMapping("api/v3/AvailableCountries")
    List<NagerCountryResponse> findCountries();

    @GetMapping("api/v3/PublicHolidays/{year}/{countryCode}")
    List<NagerHolidayResponse> findHolidaysByYearAndCountryCode(@PathVariable("year") int year,
                                                                @PathVariable("countryCode") String countryCode);
}
