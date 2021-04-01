package com.webservice.bookstore.config.security.oauth2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.config.security.jwt.JwtProperties;
import com.webservice.bookstore.config.security.jwt.JwtUtil;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private ObjectMapper objectMapper = new ObjectMapper();

    private String writeTimeNow() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        log.info(customUserDetails.getMember());
        String email    = customUserDetails.getMember().getEmail();
        String nickName = customUserDetails.getMember().getNickName();

        log.info("JwtAuthenticationFilter.successfulAuthentication : 'OK'");

//        response.setHeader(JwtProperties.HEADER_STRING,
//                         JwtProperties.TOKEN_PREFIX + jwtUtil.createAccessToken(email, nickName));
        log.info("request.getRequestURI() : " + request.getRequestURI());
        log.info("request.getParameter(\"redirect_uri\") : " + request.getParameter("redirect_uri"));
//        String targetUrl = String.format("/api/oauth2/success?email=%s&nickName=%s",
//                                            email, URLEncoder.encode(nickName, "UTF-8"));
//        getRedirectStrategy().sendRedirect(request, response, targetUrl);

//        if(customUserDetails.isEnabled()) {
//            // Refresh 토큰 생성하여 Redis에 저장
//            redisUtil.setData(email, jwtUtil.createRefreshToken(email), 60L*20);
//
//            response.setStatus(HttpStatus.OK.value());
//            response.setContentType("application/json;charset=utf-8");
//            response.setHeader(JwtProperties.HEADER_STRING,
//                         JwtProperties.TOKEN_PREFIX
//                                + jwtUtil.createAccessToken(customUserDetails.getMember().getEmail(),
//                                                            customUserDetails.getMember().getNickName()));
//
//            Map<String, Object> resultAttributes = new HashMap<>();
//            resultAttributes.put("timestamp", writeTimeNow());
//            resultAttributes.put("status", HttpStatus.OK);
//            resultAttributes.put("message", "Authentication completed (OAuth)");
//            resultAttributes.put("path", request.getRequestURI());
//
//            response.getWriter().println(objectMapper.writeValueAsString(resultAttributes));
//
//            // java.io.CharConversionException 에러는 tomcat에서 발생하는 예외이다.
//            // 발생하는 이유는 tomcat Encoding 설정이 ISO-8859-1 형식으로 되어있기 때문에,
//            // 별도로 tomcat 설정을 수정하거나 ServletOutputStream 객체가 아닌 위처럼 PrintWriter 객체로 넘겨주어야한다.
//            //response.getOutputStream().println(objectMapper.writeValueAsString(resultAttributes));
//
//        } else {
//            response.setStatus(HttpStatus.LOCKED.value()); // 423 응답값
//            response.setContentType("application/json;charset=utf-8");
//
//            Map<String, Object> resultAttributes = new HashMap<>();
//            resultAttributes.put("timestamp", writeTimeNow());
//            resultAttributes.put("status", HttpStatus.LOCKED);
//            resultAttributes.put("message", "This Email is locked (OAuth)");
//            resultAttributes.put("path", request.getRequestURI());
//
//            response.getWriter().println(objectMapper.writeValueAsString(resultAttributes));
//
//            SecurityContextHolder.getContext().setAuthentication(null);
//        }

    }

}
