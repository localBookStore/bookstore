package com.webservice.bookstore.config.security.jwt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public interface JwtProperties {

    String HEADER_STRING = "Authorization";
    String TOKEN_PREFIX  = "Bearer ";
    String SECRET        = Base64.getEncoder().encodeToString("ejdejfjfjznf".getBytes()); // 덩더러러쿨

    List<String> OAUTH2_AUTHORIZED_REDIRECT_URIS = new ArrayList<>(Arrays.asList("http://localhost:3000/oauth2/redirect"));

}
