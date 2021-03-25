package com.webservice.bookstore.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.Instant;
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

    public String createAccessToken(String email, String nickName) {

        return JWT.create()
                .withSubject(email)
                .withClaim("nickName", nickName)
                .withClaim("exp", Instant.now().getEpochSecond() + 60)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

    }

    public String createRefreshToken(String email) {

        return JWT.create()
                .withSubject(email)
//                .withExpiresAt(expiresAt(30))
                .withClaim("exp", Instant.now().getEpochSecond() + 70)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

    }

    public VerifyResult verify(String jwtToken) {

        log.info("Input jwtToken : " + jwtToken);

        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                                        .build()
                                        .verify(jwtToken);

            log.info("JWT decoding successful");
            return VerifyResult.builder()
                                .email(decodedJWT.getSubject())
                                .nickName(String.valueOf(decodedJWT.getClaim("nickName")))
                                .result(true)
                                .build();

        } catch (JWTVerificationException e) {
            log.info("JWT has expired");
            DecodedJWT decodedJWT = JWT.decode(jwtToken);

            return VerifyResult.builder()
                                .email(decodedJWT.getSubject())
                                .result(false)
                                .build();
        } catch (NullPointerException e) {  // Redis에 특정 Refresh 토큰이 존재하지 않는 경우(null) 예외 발생
            log.info("JWT does not exist.");
            return VerifyResult.builder()
                                .result(false)
                                .build();
        }

    }

}
