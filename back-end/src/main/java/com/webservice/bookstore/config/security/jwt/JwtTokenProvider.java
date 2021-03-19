package com.webservice.bookstore.config.security.jwt;

import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.config.security.auth.CustomUserDetailsService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private String secretKey = "ejdejfjfjznf";  // 덩더러러쿨
    private final Long ACCESS_TOKEN_VALID_TIME  = 1000L*60;      // Access Token 유효 기간  : 30분(1000L*60*30)
    private final Long REFRESH_TOKEN_VALID_TIME = 1000L*60*3;    // Refresh Token 유효 기간 : 2주(1000L*60*60*24*14)

    private final CustomUserDetailsService customUserDetailsService;

    // @PostConstruct가 붙은 메서드는 클래스가 @Service를 수행하기 전에 발생
    @PostConstruct
    protected void initialize() {
        // byte[] 형식에서 String으로 변환
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAccessToken(String userId, String role) {

        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("role", role);

        return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALID_TIME))  // 지금 시간으로부터 30분 추가
                    .signWith(SignatureAlgorithm.HS512, secretKey)
                    .compact();
    }

    public String createRefreshToken(String value) {

        Claims claims = Jwts.claims();
        claims.put("value", value);

        return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALID_TIME))  // 지금 시간으로부터 2주일 추가
                    .signWith(SignatureAlgorithm.HS512, secretKey)
                    .compact();
    }

    // JWT 토큰 유효성 검증
    public boolean isTokenValid(String jwtToken) {
        try {
            Claims claims = getClaimsFromJwtToken(jwtToken);
            return !claims.getExpiration().before(new Date());  // 작성된 유효 기간이 현재 시간을 지났으면 false
        } catch (Exception e) {
            return false;
        }
    }

    // JWT 토큰으로 인증
    public Authentication getAuthentication(String jwtToken) {

        log.info("Input JWT Token : " + jwtToken);

        String userid = getClaimsFromJwtToken(jwtToken).getSubject();

        CustomUserDetails userDetails
                = (CustomUserDetails) customUserDetailsService.loadUserByUsername(userid);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private Claims getClaimsFromJwtToken(String jwtToken) throws JwtException {

        Claims claims = Jwts.parser()
                            .setSigningKey(secretKey)
                            .parseClaimsJws(jwtToken)
                            .getBody();

        log.info("Print Claims : " + claims);

        return claims;
    }

}
