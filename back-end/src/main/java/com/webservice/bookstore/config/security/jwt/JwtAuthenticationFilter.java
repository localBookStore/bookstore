package com.webservice.bookstore.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.domain.entity.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        System.out.println("JwtAuthenticationFilter의 attemptAuthentication() 호출 : 로그인 시도 중...");

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            Member member = objectMapper.readValue(request.getInputStream(), Member.class);
            System.out.println("member : " + member);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword());
            System.out.println("authenticationToken : " + authenticationToken);

            // authentication에 로그인한 정보가 담긴다.
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 권한(Role) 확인을 위해 세션에 임시로 저장하여 successfulAuthentication/unsuccessfulAuthentication 메소드에 넘겨줌
            CustomUserDetails customUserDetails =
                    (CustomUserDetails) authentication.getPrincipal();
            System.out.println("로그인 완료 (customUserDetails.getMember().getEmail()) : " + customUserDetails.getMember().getEmail());

            return authentication;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {

        log.info("정상적으로 로그인 인증 완료, JWT 생성");
//        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//
//        String userid = customUserDetails.getMember().getUserid();
//        String name = customUserDetails.getMember().getName();
//        String role = String.valueOf(customUserDetails.getMember().getRole());

        String jwtToken = jwtTokenProvider.createAccessToken(authentication);
        System.out.println("JWT 생성 : " + jwtToken);

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        // 401(Unauthorized) 상태 발생
        log.info("unsuccessfulAuthentication (Unauthorized)");
        super.unsuccessfulAuthentication(request, response, failed);
    }





//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        String jwtToken = request.getHeader("Authorization");
//        // JWT 토큰을 검증해서 정상적인 사용자인지 확인 (헤더값 확인)
//        if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
//            // JWT 토큰값이 없거나 'Bearer ' 문자열로 시작하지 않는다면 다음 필터로 넘겨줌
//            filterChain.doFilter(request, response);
//            return;
//        }
//        jwtToken = jwtToken.replace("Bearer ", "");
//
//        if (jwtToken != null && jwtTokenProvider.isTokenValid(jwtToken)) { // jwt이 not null이면서 유효 기간이 지나지 않았다면,
//            Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        filterChain.doFilter(request, response);
//    }

}
