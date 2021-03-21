package com.webservice.bookstore.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.domain.entity.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.info("JwtAuthenticationFilter의 attemptAuthentication() 호출 : 로그인 시도 중...");

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
            log.info("로그인 완료 (customUserDetails.getMember().getEmail()) : " + customUserDetails.getMember().getEmail());

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

        log.info("JwtAuthenticationFilter.successfulAuthentication : 'OK'");

        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=utf-8");
        response.setHeader(JwtProperties.HEADER_STRING,
                JwtProperties.TOKEN_PREFIX + jwtTokenProvider.createAccessToken(authentication));

        Map<String, Object> resultAttributes = new HashMap<>();
        resultAttributes.put("timestamp", OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        resultAttributes.put("status", HttpStatus.OK);
        resultAttributes.put("message", "Authentication completed (Default)");
        resultAttributes.put("path", request.getRequestURI());

        response.getWriter().println(objectMapper.writeValueAsString(resultAttributes));

        // java.io.CharConversionException 에러는 tomcat에서 발생하는 예외이다.
        // 발생하는 이유는 tomcat Encoding 설정이 ISO-8859-1 형식으로 되어있기 때문에,
        // 별도로 tomcat 설정을 수정하거나 ServletOutputStream 객체가 아닌 위처럼 PrintWriter 객체로 넘겨주어야한다.
        //response.getOutputStream().println(objectMapper.writeValueAsString(resultAttributes));

//        super.successfulAuthentication(request, response, chain, authentication);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        // 401(Unauthorized) 상태 발생
        log.info("JwtAuthenticationFilter.unsuccessfulAuthentication : 'Unauthorized'");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=utf-8");

        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("timestamp", OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        errorAttributes.put("status", HttpStatus.UNAUTHORIZED);
        errorAttributes.put("exception", failed.getMessage());
        errorAttributes.put("path", request.getRequestURI());

        response.getWriter().println(objectMapper.writeValueAsString(errorAttributes));

        // java.io.CharConversionException 에러는 tomcat에서 발생하는 예외이다.
        // 발생하는 이유는 tomcat Encoding 설정이 ISO-8859-1 형식으로 되어있기 때문에,
        // 별도로 tomcat 설정을 수정하거나 ServletOutputStream 객체가 아닌 위처럼 PrintWriter 객체로 넘겨주어야한다.
        //response.getOutputStream().println(objectMapper.writeValueAsString(errorAttributes));

//        super.unsuccessfulAuthentication(request, response, failed);
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
