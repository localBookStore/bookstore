package com.webservice.bookstore.config.security.oauth2.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${check.envname}")
    private String evn;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String redirectUrl = "http://localhost:3000/oauth?error=";
        if(evn.equals("deploy")) {
            redirectUrl = "https://www.books25.shop/oauth?error=";
        }

        getRedirectStrategy().sendRedirect(request, response, redirectUrl + exception.getMessage());
    }
}