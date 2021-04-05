package com.webservice.bookstore.config.security.logout.handler;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webservice.bookstore.config.security.jwt.JwtProperties;
import com.webservice.bookstore.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

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
public class CustomLogoutSuccessfulHandler implements LogoutSuccessHandler {

    private final RedisUtil redisUtil;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String token = request.getHeader(JwtProperties.HEADER_STRING);

        if (StringUtils.isNotBlank(token)) {
            token = token.substring(JwtProperties.TOKEN_PREFIX.length());
            String email = JWT.decode(token).getSubject();

            // Redis 존재하는 Refresh 토큰 제거
            redisUtil.deleteData(email);
        }

        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=utf-8");

        Map<String, Object> resultAttributes = new HashMap<>();
        resultAttributes.put("timestamp", LocalDateTime.now()
                                                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        resultAttributes.put("status", HttpStatus.OK);
        resultAttributes.put("message", "Logout completed");
        resultAttributes.put("path", request.getRequestURI());

        response.getWriter().println(objectMapper.writeValueAsString(resultAttributes));

        response.setStatus(HttpStatus.OK.value());

    }
}
