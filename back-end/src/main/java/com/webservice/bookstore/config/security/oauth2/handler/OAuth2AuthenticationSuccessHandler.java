package com.webservice.bookstore.config.security.oauth2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.config.security.jwt.JwtProperties;
import com.webservice.bookstore.config.security.jwt.JwtUtil;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.exception.BadRequestException;
import com.webservice.bookstore.util.CookieUtil;
import com.webservice.bookstore.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.webservice.bookstore.config.security.oauth2.handler.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Log4j2
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    private String writeTimeNow() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        log.info("OAuth2 clearAuthenticationAttributes 메소드 호출");
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    protected String determineTargetUrl(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) {

        log.info("OAuth2 determineTargetUrl 메소드 호출");
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        log.info(customUserDetails.getMember());
        String email    = customUserDetails.getMember().getEmail();
        String nickName = customUserDetails.getMember().getNickName();
        String role     = String.valueOf(customUserDetails.getMember().getRole());

        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                                                 .map(Cookie::getValue);
        log.info("redirectUri : " + redirectUri);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        String token = jwtUtil.createAccessToken(email, nickName, role);

        String targetUrlToken = UriComponentsBuilder.fromUriString(targetUrl)
                                                    .queryParam("token", token)
                                                    .build().toUriString();
        return targetUrlToken;
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        log.info("OAuth2 isAuthorizedRedirectUri 메소드 호출");
        URI clientRedirectUri = URI.create(uri);
        log.info("clientRedirectUri : " + clientRedirectUri);

        return JwtProperties.OAUTH2_AUTHORIZED_REDIRECT_URIS.stream()
                                .anyMatch(authorizedRedirectUri -> {
                                    // Only validate host and port. Let the clients use different paths if they want to
                                    URI authorizedURI = URI.create(authorizedRedirectUri);
                                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                                                    && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                                        log.info("");
                                        return true;
                                    }
                                    log.info("");
                                    return false;
                                });
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.info("JwtAuthenticationFilter.successfulAuthentication : 'OK'");
        log.info("request.getRequestURI() : " + request.getRequestURI());

        String targetUrl = determineTargetUrl(request, response, authentication);
        log.info("targetUrl : " + targetUrl);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

//        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//        log.info(customUserDetails.getMember());
//        String email    = customUserDetails.getMember().getEmail();
//        String nickName = customUserDetails.getMember().getNickName();
//        String role     = String.valueOf(customUserDetails.getMember().getRole());
//
//        clearAuthenticationAttributes(request, response);
//
//        if(customUserDetails.isEnabled()) {
//            // Refresh 토큰 생성하여 Redis에 저장
//            redisUtil.setData(email, jwtUtil.createRefreshToken(email), 60L*20);
//
//            response.setStatus(HttpStatus.OK.value());
//            response.setContentType("application/json;charset=utf-8");
//            response.setHeader(JwtProperties.HEADER_STRING,
//                         JwtProperties.TOKEN_PREFIX
//                                + jwtUtil.createAccessToken(email, nickName, role));
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
//            getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000/oauth2/redirect");
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
//            getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000/oauth2/redirect");
//        }
//
    }

}
