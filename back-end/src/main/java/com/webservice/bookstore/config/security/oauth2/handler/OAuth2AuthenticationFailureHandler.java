package com.webservice.bookstore.config.security.oauth2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        log.info("OAuth2AuthenticationSuccessHandler.onAuthenticationFailure : 'Unauthorized'");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=utf-8");

        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("timestamp", OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        errorAttributes.put("status", HttpStatus.UNAUTHORIZED);
        errorAttributes.put("exception", exception.getMessage());
        errorAttributes.put("path", request.getRequestURI());

        response.getWriter().println(objectMapper.writeValueAsString(errorAttributes));
//        response.getWriter().write(objectMapper.writeValueAsString(errorAttributes)); // response 바디에 json 으로 담기


        // java.io.CharConversionException 에러는 tomcat에서 발생하는 예외이다.
        // 발생하는 이유는 tomcat Encoding 설정이 ISO-8859-1 형식으로 되어있기 때문에,
        // 별도로 tomcat 설정을 수정하거나 ServletOutputStream 객체가 아닌 위처럼 PrintWriter 객체로 넘겨주어야한다.
        //response.getOutputStream().println(objectMapper.writeValueAsString(errorAttributes ));

    }
}
