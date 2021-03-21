package com.webservice.bookstore.config.security.oauth2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webservice.bookstore.config.security.jwt.JwtProperties;
import com.webservice.bookstore.config.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.info("OAuth2AuthenticationSuccessHandler.onAuthenticationSuccess : 'OK'");

        response.setStatus(HttpStatus.OK.value());

        String jwtToken = JwtProperties.TOKEN_PREFIX + jwtTokenProvider.createAccessToken(authentication);
        response.setHeader(JwtProperties.HEADER_STRING, jwtToken);
        response.setContentType("application/json;charset=utf-8");

        Map<String, Object> resultAttributes = new HashMap<>();
        resultAttributes.put("timestamp", OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        resultAttributes.put("status", HttpStatus.OK);
        resultAttributes.put("message", "Authentication completed (Default)");
        resultAttributes.put("path", request.getRequestURI());

        response.getWriter().println(objectMapper.writeValueAsString(resultAttributes));

        // java.io.CharConversionException 에러는 tomcat에서 발생하는 예외이다.
        // 발생하는 이유는 tomcat Encoding 설정이 ISO-8859-1 형식으로 되어있기 때문에,
        // 별도로 tomcat 설정을 수정하거나 ServletOutputStream 객체가 아닌 위처럼 PrintWriter 객체로 넘겨주어야한다.
        // response.getOutputStream().println(objectMapper.writeValueAsString(resultAttributes));

    }

}
