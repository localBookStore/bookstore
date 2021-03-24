package com.webservice.bookstore.config.security.jwt;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public interface JwtProperties {

//    Long ACCESS_TOKEN_VALID_TIME  = System.currentTimeMillis() + 1000L*60;   // Access Token 유효 기간  : 현재 시간으로부터 60초
//    Long REFRESH_TOKEN_VALID_TIME = System.currentTimeMillis() + 1000L*60*3; // Refresh Token 유효 기간  : 현재 시간으로부터 3분

    String HEADER_STRING = "Authorization";
    String TOKEN_TYPE    = "JWT";
    String TOKEN_PREFIX  = "Bearer ";
    String SECRET        = Base64.getEncoder().encodeToString("ejdejfjfjznf".getBytes()); // 덩더러러쿨


}
