package com.webservice.bookstore.config.security.jwt;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public interface JwtProperties {
    String SECRET = Base64.getEncoder().encodeToString("ejdejfjfjznf".getBytes()); // 덩더러러쿨
    Long ACCESS_TOKEN_VALID_TIME = Instant.now().getEpochSecond() + 60;    // Access Token 유효 기간  : 현재 시간으로부터 60초
    Long REFRESH_TOKEN_VALID_TIME = Instant.now().getEpochSecond() + 60*3;    // Access Token 유효 기간  : 3분
    String TOKEN_TYPE = "JWT";
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";

    // OAuth2 Provider를 사용하여 인증한 후,
    // 사용자에 대한 인증 토큰을 생성하고 '/oauth2/authorization' 요청에서
    // Front-End Client가 언급한 redirectUri로 토큰을 전송한다.
    // 참고로, 모바일에서는 쿠키가 잘 작동하지 않기 때문에 쿠키를 사용하지 않는다고 한다.
    List<String> OAUTH2_AUTHORIZED_REDIRECT_URIS = new ArrayList<>(Arrays.asList("http://localhost:8080/oauth2/redirect"));

}
