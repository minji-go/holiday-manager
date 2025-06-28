package com.example.feign;

import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

@Configuration
public class NagerClientConfig {
    @Bean
    public Decoder feignDecoder() {
        ObjectFactory<HttpMessageConverters> messageConverters = () -> {
            MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
            jacksonConverter.setSupportedMediaTypes(List.of(
                    MediaType.APPLICATION_JSON,
                    MediaType.valueOf("text/json"),
                    MediaType.valueOf("text/json;charset=UTF-8")
            ));

            return new HttpMessageConverters(jacksonConverter);
        };

        return new ResponseEntityDecoder(new SpringDecoder(messageConverters));
    }
}
