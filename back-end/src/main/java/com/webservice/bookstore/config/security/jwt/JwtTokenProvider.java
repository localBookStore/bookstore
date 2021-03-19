package com.webservice.bookstore.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.config.security.auth.CustomUserDetailsService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final CustomUserDetailsService customUserDetailsService;

//    public String createAccessToken(String userid, String name, String role) {
    public String createAccessToken(Authentication authentication) {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        return JWT.create()
                .withSubject(customUserDetails.getMember().getEmail())
                .withClaim("exp", JwtProperties.ACCESS_TOKEN_VALID_TIME)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

//        Claims claims = Jwts.claims();
//        claims.put("userid", customUserDetails.getMember().getUserid());
//
//        return Jwts.builder()
//                    .setHeaderParam("typ", JwtProperties.TOKEN_TYPE)
//                    .setClaims(claims)
//                    .setIssuedAt(new Date(System.currentTimeMillis()))
//                    .setExpiration(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_TOKEN_VALID_TIME))
//                    .signWith(SignatureAlgorithm.HS512, JwtProperties.SECRET)
//                    .compact();
    }

    public String createRefreshToken(Authentication authentication) {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        return JWT.create()
                .withSubject(customUserDetails.getMember().getEmail())
                .withClaim("exp", JwtProperties.REFRESH_TOKEN_VALID_TIME)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));


//        Claims claims = Jwts.claims();
//        claims.put("value", value);
//
//        return Jwts.builder()
//                    .setHeaderParam("typ", JwtProperties.TOKEN_TYPE)
//                    .setClaims(claims)
//                    .setIssuedAt(new Date(System.currentTimeMillis()))
//                    .setExpiration(new Date(System.currentTimeMillis() + JwtProperties.REFRESH_TOKEN_VALID_TIME))
//                    .signWith(SignatureAlgorithm.HS512, JwtProperties.SECRET)
//                    .compact();
    }

    public VerifyResult verify(String jwtToken) {

        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                                        .build().verify(jwtToken);

            return VerifyResult.builder()
                                .email(decodedJWT.getSubject())
                                .result(true)
                                .build();

        } catch (JWTVerificationException e) {

            DecodedJWT decodedJWT = JWT.decode(jwtToken);

            return VerifyResult.builder()
                                .email(decodedJWT.getSubject())
                                .result(false)
                                .build();
        }

    }

    // Get Payload from Input JWT
    private Claims getClaimsFromJwtToken(String jwtToken) throws JwtException {

        Claims claims = Jwts.parser()
                            .setSigningKey(JwtProperties.SECRET)
                            .parseClaimsJws(jwtToken)

                            .getBody();

        return claims;
    }


    //    // JWT 토큰 서명 확인 후 정상이면 Authentication 객체 생성
//    public Authentication getAuthentication(String jwtToken) {
//
//        String userid = getClaimsFromJwtToken(jwtToken).get("email").toString();
//
//        CustomUserDetails userDetails
//                = (CustomUserDetails) customUserDetailsService.loadUserByUsername(userid);
//
//        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//    }

    // JWT 토큰 유효성 검증
//    public boolean isTokenValid(String jwtToken) {
//        try {
//            Claims claims = getClaimsFromJwtToken(jwtToken);
//            return !claims.getExpiration().before(new Date());  // 작성된 유효 기간이 현재 시간을 지났으면 false
//        } catch (SignatureException e) {
//            log.error("유효하지 않은 JWT 서명");
//        } catch (MalformedJwtException e) {
//            log.error("유효하지 않은 JWT 토큰");
//        } catch (ExpiredJwtException e) {
//            log.error("만료된 JWT 토큰");
//        } catch (UnsupportedJwtException e) {
//            log.error("지원하지 않는 JWT 토큰");
//        } catch (IllegalArgumentException e) {
//            log.error("JWT is Empty");
//        }
//        return false;
//    }


}
