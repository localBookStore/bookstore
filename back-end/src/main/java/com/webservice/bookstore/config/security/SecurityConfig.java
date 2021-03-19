package com.webservice.bookstore.config.security;

import com.webservice.bookstore.config.security.auth.CustomUserDetailsService;
import com.webservice.bookstore.config.security.jwt.JwtAuthenticationFilter;
import com.webservice.bookstore.config.security.jwt.JwtAuthorizationFilter;
import com.webservice.bookstore.config.security.jwt.JwtTokenProvider;
import com.webservice.bookstore.config.security.oauth2.CustomOAuth2UserService;
import com.webservice.bookstore.config.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.webservice.bookstore.config.security.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizationSuccessHandler;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomUserDetailsService customUserDetailsService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .addFilter(corsFilter)
            .addFilterBefore(new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider),
                    UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider, customUserDetailsService),
                    BasicAuthenticationFilter.class)
            .formLogin().disable()
            .httpBasic().disable()
            .authorizeRequests()
                .antMatchers("/api/admin/**").access("hasRole('ADMIN')")
                .anyRequest().permitAll()
                .and()
            .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    // 특정 권한만 접근할 수 있는 페이지에 대해 로그인 없이 접근하려고 하면 아래 메소드가 호출
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "FORBIDDEN");
                    }
                })
//                .accessDeniedHandler(new AccessDeniedHandler() {
//                    // 특정 권한만 접근할 수 있는 페이지에 대해 접근 권한이 없는 계정이 접근하려고 하면 아래 메소드가 호출
//                    @Override
//                    public void handle(HttpServletRequest request,
//                                       HttpServletResponse response,
//                                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
//                        System.out.println("Role Deny access!!!!!!!!!!!!!");
//                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Deny Access");
//                    }
//                })
                .and()
            .oauth2Login()
                .authorizationEndpoint()
                    .baseUri("/oauth2/authorization")
                    .authorizationRequestRepository(httpCookieAuthorizationRequestRepository())
                    .and()
                .redirectionEndpoint()
                    .baseUri("/login/oauth2/code/*")
                    .and()
                .userInfoEndpoint()
                    .userService(customOAuth2UserService)
                    .and()
                .successHandler(oAuth2AuthenticationSuccessHandler);     // OAuth2 인증 성공 시 해당 핸들러 수행
//            .failureHandler();    // OAuth2 인증 실패 시 해당 핸들러 수행


    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /*
    Spring OAuth2는 기본적으로 HttpSessionOAuth2AuthorizationRequestRepository 구현체를
    사용해 Authorization Request를 저장한다.
    우리는 JWT를 사용하므로, Session에 이를 저장할 필요가 없다.
    따라서 custom으로 구현한 HttpCookieOAuth2AuthorizationRequestRepository를 사용하여
    Authorization Reqeust를 Based64 encoded cookie에 저장한다.

    참고 : https://ozofweird.tistory.com/m/586
    */
    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> httpCookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

}
