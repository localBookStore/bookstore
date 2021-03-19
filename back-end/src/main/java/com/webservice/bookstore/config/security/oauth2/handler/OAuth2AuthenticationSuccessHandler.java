package com.webservice.bookstore.config.security.oauth2.handler;

import com.webservice.bookstore.config.security.jwt.JwtProperties;
import com.webservice.bookstore.config.security.jwt.JwtTokenProvider;
import com.webservice.bookstore.config.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.webservice.bookstore.exception.BadRequestException;
import com.webservice.bookstore.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.webservice.bookstore.config.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Log4j2
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.info("OAuth2 onAuthenticationSuccess 메소드 호출");
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            log.debug("응답이 이미 커밋되었습니다. " + targetUrl + "로 리다이렉션을 할 수 없습니다");
            return;
        }

        int idx = targetUrl.indexOf(JwtProperties.TOKEN_PREFIX);
        String token = targetUrl.replace(targetUrl.substring(0, idx), "");

        HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
        HttpPost post = new HttpPost("http://localhost:8080" + targetUrl.replace(" ", "%20"));
        post.setHeader("Authorization", token);

        HttpResponse httpResponse = client.execute(post);

        clearAuthenticationAttributes(request, response);

//        getRedirectStrategy().sendRedirect(request, response, targetUrl);
//        super.onAuthenticationSuccess(request, response, authentication);

    }

    protected String determineTargetUrl(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) {

        log.info("OAuth2 determineTargetUrl 메소드 호출");
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                                                 .map(Cookie::getValue);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException("승인되지 않은 리디렉션 URI가 있어 인증을 진행할 수 없습니다.");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        log.info("targetUrl : " + targetUrl);

        String jwtToken = tokenProvider.createAccessToken(authentication);

        return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("token", JwtProperties.TOKEN_PREFIX + jwtToken)
                    .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        log.info("OAuth2 clearAuthenticationAttributes 메소드 호출");
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
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
                            return true;
                        }

                        return false;
                    });
    }
}
