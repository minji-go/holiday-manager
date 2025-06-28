package com.example.config;

import com.example.feign.NagerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = NagerClient.class)
public class FeignClientConfig {
}
