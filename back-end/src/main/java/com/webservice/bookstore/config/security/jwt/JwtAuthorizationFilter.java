package com.webservice.bookstore.config.security.jwt;

import com.auth0.jwt.exceptions.TokenExpiredException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.config.security.auth.CustomUserDetailsService;
import com.webservice.bookstore.util.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

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
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtUtil jwtUtil;
    private RedisUtil redisUtil;
    private CustomUserDetailsService customUserDetailsService;
    private ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  JwtUtil jwtUtil, RedisUtil redisUtil,
                                  CustomUserDetailsService customUserDetailsService) {
        super(authenticationManager);
        this.jwtUtil    = jwtUtil;
        this.redisUtil      = redisUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    private void saveAuthSecuritySession(VerifyResult verifyResult) {
        log.info("Query Member By JWT Subject(email) :");
        CustomUserDetails customUserDetails
                = (CustomUserDetails) customUserDetailsService.loadUserByUsername(verifyResult.getEmail());
        Authentication auth
                = new UsernamePasswordAuthenticationToken(customUserDetails ,null, customUserDetails.getAuthorities());

        log.info("Save Authentication in SecuritySession :");
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        log.info("JwtAuthorizationFilter.doFilterInternal :");

        String authorizationValue = request.getHeader(JwtProperties.HEADER_STRING);  // jwt 토큰 값

        // JWT 토큰 값이 없거나 'Bearer ' 문자열로 시작하지 않는다면 다음 필터로 넘겨줌
        if (StringUtils.isEmpty(authorizationValue) || !authorizationValue.startsWith(JwtProperties.TOKEN_PREFIX)) {
            log.info("No JWT, JWT structure issue :");
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = authorizationValue.replace(JwtProperties.TOKEN_PREFIX, "");

        log.info("Verify the input Access Token :");
        VerifyResult verifyResult = jwtUtil.verify(jwtToken);
        if(verifyResult.isResult()) {
            log.info("The input Access Token is valid! :");
            saveAuthSecuritySession(verifyResult);

        } else {
            log.info("The input Access Token is invalid... :");
            String email = verifyResult.getEmail();
            String nickName = verifyResult.getNickName();
            String role = verifyResult.getRole();

            String refreshToken = redisUtil.getData(email);
            log.info("Saved Refresh Token in Redis : {}", refreshToken);

            log.info("Verify the Refresh Token :");
            VerifyResult refreshVerify = jwtUtil.verify(refreshToken);
            if(refreshVerify.isResult()) {
                log.info("The Refresh Token is valid! Create new Access Token :");
                String newAccessToken
                        = jwtUtil.createAccessToken(refreshVerify.getEmail(), nickName, role);
                response.setHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + newAccessToken);

                saveAuthSecuritySession(verifyResult);

            } else {
                log.info("The Refresh Token is invalid... :");
                redisUtil.deleteData(email);
                RuntimeException e = new TokenExpiredException("The Refresh Token is invalid");
                this.onUnsuccessfulAuthentication(
                            request, response, new AuthenticationException(e.getMessage(), e.getCause()) {}
                        );
                return;
            }

        }

        filterChain.doFilter(request,response);

    }

    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request,
                                                HttpServletResponse response,
                                                AuthenticationException failed) throws IOException {

        SecurityContextHolder.getContext().setAuthentication(null);

        // 401(Unauthorized) 상태 발생
        log.info("JwtAuthorizationFilter.onUnsuccessfulAuthentication : 'Expired'");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=utf-8");

        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("timestamp", OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        errorAttributes.put("status", HttpStatus.UNAUTHORIZED);
        errorAttributes.put("exception", failed.getMessage());
        errorAttributes.put("path", request.getRequestURI());

        response.getWriter().println(objectMapper.writeValueAsString(errorAttributes));

    }
}
