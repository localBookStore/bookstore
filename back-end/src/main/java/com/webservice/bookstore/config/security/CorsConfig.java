package com.webservice.bookstore.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");   // 모두 IP 주소에 응답을 허용한다
        config.addAllowedHeader("*");   // 모든 Header에 응답을 허용한다.
        config.addExposedHeader("Authorization");
        config.addAllowedMethod("*");   // 모든 Method(GET, POST, PATCH, DELETE) 요청을 허용한다.
        source.registerCorsConfiguration("/api/**", config);
        source.registerCorsConfiguration("/login", config);
        source.registerCorsConfiguration("/logout", config);

        return new CorsFilter(source);

    }
}
