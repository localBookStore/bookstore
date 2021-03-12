package com.webservice.bookstore.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean //modelmapper는 공용으로 사용하기 때문에 bean 으로 등록해서 사용
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
