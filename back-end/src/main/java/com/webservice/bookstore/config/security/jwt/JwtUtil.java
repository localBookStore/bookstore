package com.webservice.bookstore.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private Date expiresAt(Integer... nums)  {
        // 1000L -> 1초, 1000L*60 -> 1분, 1000L*60*60 -> 1시간, ...
        Long seconds = 1000L;
        for(Integer num : nums) {
            seconds*=num;
        }
        return new Date(System.currentTimeMillis() + seconds);
    }

    public String createAccessToken(CustomUserDetails customUserDetails) {

        return JWT.create()
//                .withHeader(Map.of("typ", JwtProperties.TOKEN_TYPE))    // Java 9 버전 이상 지원
                .withHeader(new HashMap<>() {{
                    put("typ", JwtProperties.TOKEN_TYPE);
                }})
                .withExpiresAt(expiresAt(5))
                .withSubject(customUserDetails.getMember().getEmail())
                .withClaim("nickName", customUserDetails.getMember().getNickName())
                .withClaim("role", String.valueOf(customUserDetails.getMember().getRole()))
                .withClaim("IssuedDate", OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

    }

    public String createRefreshToken(CustomUserDetails customUserDetails) {

        return JWT.create()
                .withHeader(new HashMap<>() {{
                    put("typ", JwtProperties.TOKEN_TYPE);
                }})
                .withExpiresAt(expiresAt(5))
                .withSubject(customUserDetails.getMember().getEmail())
                .withClaim("IssuedDate", OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

    }

    public static void verify(String jwtToken) throws JWTVerificationException {

        log.info("Input jwtToken : " + jwtToken);

        JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
            .build()
            .verify(jwtToken);

        log.info("JWT decoding successful");

    }

}
