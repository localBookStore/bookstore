package com.webservice.bookstore.config.security.jwt;

import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.config.security.auth.CustomUserDetailsService;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
@Log4j2
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        log.info("인가 확인을 위한 JwtAuthorizationFilter 동작");

        String authorizationValue = request.getHeader("Authorization");  // jwt 토큰 값
        System.out.println("request header Authorization 키값 : " + authorizationValue);

        // JWT 토큰 값이 없거나 'Bearer ' 문자열로 시작하지 않는다면 다음 필터로 넘겨줌
        if (authorizationValue == null || !authorizationValue.startsWith(JwtProperties.TOKEN_PREFIX)) {
            log.error("Value 값이 없거나, 유효하지 않은 JWT 토큰");
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = authorizationValue.replace("Bearer", "");

        VerifyResult verifyResult = jwtTokenProvider.verify(jwtToken);
        if(verifyResult.isResult()) {
            log.info("토큰이 유효합니다. DB를 조회합니다...");

            CustomUserDetails userDetails
                    = (CustomUserDetails) customUserDetailsService.loadUserByUsername(verifyResult.getEmail());

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

}
