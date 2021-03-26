package com.webservice.bookstore.config.security.oauth2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.config.security.jwt.JwtProperties;
import com.webservice.bookstore.config.security.jwt.JwtUtil;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
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
    private final MemberRepository memberRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = customUserDetails.getMember();
        String email = member.getEmail();

        log.info("JwtAuthenticationFilter.successfulAuthentication : 'OK'");

        if(customUserDetails.isEnabled()) {
            // Refresh 토큰 생성하여 Redis에 저장
            redisUtil.setData(email, jwtUtil.createRefreshToken(email), 60L);

            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json;charset=utf-8");
            response.setHeader(JwtProperties.HEADER_STRING,
                         JwtProperties.TOKEN_PREFIX
                                + jwtUtil.createAccessToken(customUserDetails.getMember().getEmail(),
                                                            customUserDetails.getMember().getNickName()));

            Map<String, Object> resultAttributes = new HashMap<>();
            resultAttributes.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            resultAttributes.put("status", HttpStatus.OK);
            resultAttributes.put("message", "Authentication completed (Default)");
            resultAttributes.put("path", request.getRequestURI());

            response.getWriter().println(objectMapper.writeValueAsString(resultAttributes));

            // java.io.CharConversionException 에러는 tomcat에서 발생하는 예외이다.
            // 발생하는 이유는 tomcat Encoding 설정이 ISO-8859-1 형식으로 되어있기 때문에,
            // 별도로 tomcat 설정을 수정하거나 ServletOutputStream 객체가 아닌 위처럼 PrintWriter 객체로 넘겨주어야한다.
            //response.getOutputStream().println(objectMapper.writeValueAsString(resultAttributes));

        } else {
            response.setStatus(HttpStatus.LOCKED.value()); // 423 응답값
            response.setContentType("application/json;charset=utf-8");

            Map<String, Object> resultAttributes = new HashMap<>();
            resultAttributes.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            resultAttributes.put("status", HttpStatus.LOCKED);
            resultAttributes.put("message", "Email authentication has not yet been performed.");
            resultAttributes.put("path", request.getRequestURI());

            response.getWriter().println(objectMapper.writeValueAsString(resultAttributes));

            SecurityContextHolder.getContext().setAuthentication(null);
        }

//        log.info("OAuth2AuthenticationSuccessHandler.onAuthenticationSuccess : 'OK'");
//
//        // DB에 저장되어 있는 Refresh Token 검증
//        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//        try {
//            log.info("Call jwtTokenProvider.verify");
//            jwtUtil.verify(customUserDetails.getMember().getRefreshTokenValue());
//        } catch (JWTVerificationException e) { // 토큰 만료 시
//            log.info("JWTVerificationException : " + e.getMessage());
//            String newRefreshToken = jwtUtil.createRefreshToken(customUserDetails.getMember().getEmail());
//            customUserDetails.getMember().updateRefreshToken(newRefreshToken);
//            memberRepository.save(customUserDetails.getMember());
//        }
//
//        // 이메일 인증 여부 확인 (enabled 필드 값 TRUE/FALSE 확인)
//        if(customUserDetails.getMember().getEnabled().booleanValue()) {
//
//            response.setStatus(HttpStatus.OK.value());
//
//            String jwtToken = JwtProperties.TOKEN_PREFIX + jwtUtil.createAccessToken(customUserDetails.getMember().getEmail());
//            response.setHeader(JwtProperties.HEADER_STRING, jwtToken);
//            response.setContentType("application/json;charset=utf-8");
//
//            Map<String, Object> resultAttributes = new HashMap<>();
//            resultAttributes.put("timestamp", OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
//            resultAttributes.put("status", HttpStatus.OK);
//            resultAttributes.put("message", "Authentication completed (OAuth2.0)");
//            resultAttributes.put("path", request.getRequestURI());
//
//            response.getWriter().println(objectMapper.writeValueAsString(resultAttributes));
//
//            // java.io.CharConversionException 에러는 tomcat에서 발생하는 예외이다.
//            // 발생하는 이유는 tomcat Encoding 설정이 ISO-8859-1 형식으로 되어있기 때문에,
//            // 별도로 tomcat 설정을 수정하거나 ServletOutputStream 객체가 아닌 위처럼 PrintWriter 객체로 넘겨주어야한다.
//            // response.getOutputStream().println(objectMapper.writeValueAsString(resultAttributes));
//
//        } else {
//
//            response.setStatus(HttpStatus.LOCKED.value()); // 423 응답값
//            response.setContentType("application/json;charset=utf-8");
//
//            Map<String, Object> resultAttributes = new HashMap<>();
//            resultAttributes.put("timestamp", OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
//            resultAttributes.put("status", HttpStatus.LOCKED);
//            resultAttributes.put("message", "Email authentication has not yet been performed.");
//            resultAttributes.put("path", request.getRequestURI());
//
//            response.getWriter().println(objectMapper.writeValueAsString(resultAttributes));
//
//            SecurityContextHolder.getContext().setAuthentication(null);
//
//        }

    }

}
