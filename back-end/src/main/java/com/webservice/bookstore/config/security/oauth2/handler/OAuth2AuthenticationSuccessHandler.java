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

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        log.info("OAuth2 clearAuthenticationAttributes 메소드 호출");
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    protected String determineTargetUrl(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String email    = customUserDetails.getMember().getEmail();
        String nickName = customUserDetails.getMember().getNickName();
        String role     = String.valueOf(customUserDetails.getMember().getRole());

        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                                                 .map(Cookie::getValue);

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
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = customUserDetails.getMember().getEmail();

        redisUtil.setData(email, jwtUtil.createRefreshToken(email), 60L*60*24);
        String targetUrl = determineTargetUrl(request, response, authentication);
        log.info("targetUrl : " + targetUrl);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }

}
