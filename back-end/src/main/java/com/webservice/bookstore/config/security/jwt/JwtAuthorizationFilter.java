package com.webservice.bookstore.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import io.jsonwebtoken.MalformedJwtException;

import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        log.info("JwtAuthorizationFilter.doFilterInternal : 인가 검증 시도...");

        String authorizationValue = request.getHeader("Authorization");  // jwt 토큰 값

        // JWT 토큰 값이 없거나 'Bearer ' 문자열로 시작하지 않는다면 다음 필터로 넘겨줌
        if (authorizationValue == null || !authorizationValue.startsWith(JwtProperties.TOKEN_PREFIX)) {
            log.error("JWT이 없거나, JWT 구조 문제");
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = authorizationValue.replace(JwtProperties.TOKEN_PREFIX, "");
        Optional<Member> optionalMember = null;

        try {
            VerifyResult verifyResult = jwtTokenProvider.verify(jwtToken);  // Access Token 유효성 검사

            String email = verifyResult.getEmail();
            log.info("액세스 토큰이 유효합니다. DB의 Member 테이블을 조회합니다...");

            optionalMember = checkMemberEmail(email);
//            Optional<Member> optionalMember = checkMemberEmail(email);
//            CustomUserDetails customUserDetails = new CustomUserDetails(optionalMember.get());
//
//            Authentication authentication
//                    = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (TokenExpiredException e) {
            // 액세스 토큰이 만료되었다면, Refresh Token을 검사해서 유효하면 재발급, 만료가 됬다면 로그인 창으로 이동
            log.info("JWTVerificationException : " + e.getMessage());
            String email = JWT.decode(jwtToken).getSubject();
//            Optional<Member> optionalMember = checkMemberEmail(email);
            optionalMember = checkMemberEmail(email);

            Member memberEntity = optionalMember.get();

            try {
                log.info("Call jwtTokenProvider.verify");
                jwtTokenProvider.verify(memberEntity.getRefreshTokenValue());

                String newAccessToken = jwtTokenProvider.createAccessToken(new CustomUserDetails(memberEntity));
                log.info("Create New Access Token");    // Access Token 재발급
                response.setHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + newAccessToken);
            } catch (TokenExpiredException ex) {
                log.info("JWTVerificationException : " + ex.getMessage());
                throw new TokenExpiredException(ex.getMessage());
            }

        }

        CustomUserDetails customUserDetails = new CustomUserDetails(optionalMember.get());

        Authentication authentication
                = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private Optional<Member> checkMemberEmail(String email) {

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(!optionalMember.isPresent()) {
            throw new UsernameNotFoundException(email);
        }

        return optionalMember;
    }

}
