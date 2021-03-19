package com.webservice.bookstore.config.security.oauth2;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.webservice.bookstore.util.CookieUtil;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
@Component
public class HttpCookieOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    public static final String  OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    public static final String  REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    private static final int    cookieExpireSeconds = 180;

    // 인증 쿠키 조회
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {

        log.info("HttpCookieOAuth2AuthorizationRequestRepository loadAuthorizationRequest 호출:");

        OAuth2AuthorizationRequest authorizationRequest = CookieUtil.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                .map(cookie -> CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
        log.info("authorizationRequest.getAuthorizationRequestUri() : " + authorizationRequest.getAuthorizationRequestUri());
        log.info("authorizationRequest.getAuthorizationUri() : " + authorizationRequest.getAuthorizationUri());
        log.info("authorizationRequest.getGrantType() : " + authorizationRequest.getGrantType().toString());

        return authorizationRequest;
    }

    // 인증 쿠키 저장
    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest,
                                         HttpServletRequest request, HttpServletResponse response) {

        log.info("HttpCookieOAuth2AuthorizationRequestRepository saveAuthorizationRequest 호출:");

        if (authorizationRequest == null) {
            CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
            CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
            return;
        }

        CookieUtil.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
                             CookieUtil.serialize(authorizationRequest), cookieExpireSeconds);

        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);

        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            CookieUtil.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME,
                                 redirectUriAfterLogin, cookieExpireSeconds);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return this.loadAuthorizationRequest(request);
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
    }

}
