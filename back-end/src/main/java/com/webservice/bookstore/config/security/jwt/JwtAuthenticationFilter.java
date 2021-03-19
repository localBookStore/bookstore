package com.webservice.bookstore.config.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader("Authorization");
        // JWT 토큰을 검증해서 정상적인 사용자인지 확인 (헤더값 확인)
        if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
            // JWT 토큰값이 없거나 'Bearer ' 문자열로 시작하지 않는다면 다음 필터로 넘겨줌
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = jwtToken.replace("Bearer ", "");

        if (jwtToken != null && jwtTokenProvider.isTokenValid(jwtToken)) { // jwt 토큰이 비어있거나 유효 기간이 지났으면 인증 X
            Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

}
