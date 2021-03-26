package com.webservice.bookstore.config.security.logout.handler;

import com.auth0.jwt.JWT;
import com.webservice.bookstore.config.security.jwt.JwtProperties;
import com.webservice.bookstore.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class CustomLogoutSuccessfulHandler implements LogoutSuccessHandler {

    private final RedisUtil redisUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String token = request.getHeader(JwtProperties.HEADER_STRING);

        if (token != null || token.isEmpty()) {
            log.info("로그아웃 액세스 토큰 : " + token);
            token = token.substring(JwtProperties.TOKEN_PREFIX.length());
            String email = JWT.decode(token).getSubject();

            // Redis 존재하는 Refresh 토큰
            redisUtil.deleteData(email);

        }

        response.setStatus(HttpStatus.OK.value());

    }
}
