package com.webservice.bookstore.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Member member = objectMapper.readValue(request.getInputStream(), Member.class);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 권한(Role) 확인을 위해 세션에 임시로 저장하여 un/successfulAuthentication 메소드에 넘겨줌
            return authentication;

        } catch (IOException | BadCredentialsException e) {
            throw new AuthenticationException("아이디 혹은 비밀번호가 틀렸습니다.") {};
        }
    }

    private String writeTimeNow() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = customUserDetails.getMember().getEmail();
        String nickName = customUserDetails.getMember().getNickName();
        String role = String.valueOf(customUserDetails.getMember().getRole());

        if(customUserDetails.isEnabled()) {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json;charset=utf-8");
            response.setHeader(JwtProperties.HEADER_STRING,
                    JwtProperties.TOKEN_PREFIX + jwtUtil.createAccessToken(email, nickName, role));

            Map<String, Object> resultAttributes = new HashMap<>();
            resultAttributes.put("timestamp", writeTimeNow());
            resultAttributes.put("status", HttpStatus.OK);
            resultAttributes.put("message", "Authentication completed (Default)");
            resultAttributes.put("path", request.getRequestURI());
            response.getWriter().println(objectMapper.writeValueAsString(resultAttributes));

            // 새로운 Refresh 토큰 생성 및 Redis에 저장
            redisUtil.setData(email, jwtUtil.createRefreshToken(email), 60L*60*24);
        } else {
            response.setStatus(HttpStatus.LOCKED.value()); // 423 응답값
            response.setContentType("application/json;charset=utf-8");

            Map<String, Object> resultAttributes = new HashMap<>();
            resultAttributes.put("timestamp", writeTimeNow());
            resultAttributes.put("status", HttpStatus.LOCKED);
            resultAttributes.put("message", "This Email is locked (Default)");
            resultAttributes.put("path", request.getRequestURI());
            response.getWriter().println(objectMapper.writeValueAsString(resultAttributes));

            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        log.info("JwtAuthenticationFilter.unsuccessfulAuthentication : 'Unauthorized'");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=utf-8");

        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("timestamp", writeTimeNow());
        errorAttributes.put("status", HttpStatus.UNAUTHORIZED);
        errorAttributes.put("exception", failed.getMessage());
        errorAttributes.put("path", request.getRequestURI());

        response.getWriter().println(objectMapper.writeValueAsString(errorAttributes));
    }

}
