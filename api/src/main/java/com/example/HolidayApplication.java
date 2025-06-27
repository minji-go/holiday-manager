package com.example;

import com.example.feign.NagerClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients(clients = NagerClient.class)
@SpringBootApplication
public class HolidayApplication {
    public static void main(String[] args) {
        SpringApplication.run(HolidayApplication.class, args);
    }
}
