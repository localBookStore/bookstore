package com.webservice.bookstore.config.security.jwt;

import com.auth0.jwt.exceptions.TokenExpiredException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.config.security.auth.CustomUserDetailsService;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.util.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtUtil jwtUtil;
    private RedisUtil redisUtil;
    private MemberRepository memberRepository;
    private CustomUserDetailsService customUserDetailsService;
    private ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  JwtUtil jwtUtil, RedisUtil redisUtil,
                                  MemberRepository memberRepository,
                                  CustomUserDetailsService customUserDetailsService
                                  ) {
        super(authenticationManager);
        this.jwtUtil    = jwtUtil;
        this.redisUtil      = redisUtil;
        this.memberRepository = memberRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        log.info("JwtAuthorizationFilter.doFilterInternal :");

        String authorizationValue = request.getHeader(JwtProperties.HEADER_STRING);  // jwt 토큰 값

        // JWT 토큰 값이 없거나 'Bearer ' 문자열로 시작하지 않는다면 다음 필터로 넘겨줌
        if (authorizationValue == null || !authorizationValue.startsWith(JwtProperties.TOKEN_PREFIX)) {
            log.info("No JWT, JWT structure issue :");
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = authorizationValue.replace(JwtProperties.TOKEN_PREFIX, "");
//        Optional<Member> optionalMember = null;
//        String email = JWT.decode(jwtToken).getSubject();

        VerifyResult verify = jwtUtil.verify(jwtToken);
        if(verify.isResult()) {
            log.info("액세스 토큰 검증 성공");
            CustomUserDetails customUserDetails
                    = (CustomUserDetails) customUserDetailsService.loadUserByUsername(verify.getEmail());
            log.info("조회된 닉네임 : " + customUserDetails.getMember().getNickName());
            Authentication auth
                    = new UsernamePasswordAuthenticationToken(customUserDetails ,null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(request, response);
            return;
        } else {
            log.info("액세스 토큰 검증 실패");
            String email = verify.getEmail();   // abc@gmail.com

            String refreshToken = redisUtil.getData(email);
            log.info("Saved Refresh Token in Redis : {}", refreshToken);

            VerifyResult refreshVerify = jwtUtil.verify(refreshToken);
            if(refreshVerify.isResult()) {
                log.info("리프레시 토큰 검증 성공");
                String newAccessToken = jwtUtil.createAccessToken(refreshVerify.getEmail());
                response.setHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + newAccessToken);

                CustomUserDetails customUserDetails
                        = (CustomUserDetails) customUserDetailsService.loadUserByUsername(verify.getEmail());
                Authentication auth
                        = new UsernamePasswordAuthenticationToken(customUserDetails ,null, customUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);

                filterChain.doFilter(request,response);
                return;

            } else {
                log.info("리프레시 토큰 검증 실패");
                redisUtil.deleteRefreshToken(email);
                throw new TokenExpiredException("리프레쉬 토큰 만료.");
            }

        }

//        filterChain.doFilter(request,response);


//        try {
//            log.info("Verify a Access Token :");
//            VerifyResult verifyResult = JwtUtil.verify(jwtToken);
//            log.info("The Access Token is valid. Query the member table of the Database :");
//
//            optionalMember = checkMemberEmail(verifyResult.getEmail());
//
//        } catch (JWTVerificationException e) {
//            // Access Token이 만료되었다면, Refresh Token을 검사해서 유효하면 재발급, 만료가 됬다면 로그인 창으로 이동
//            log.info("JWTVerificationException : " + e.getMessage());
//
//            optionalMember = checkMemberEmail(email);
//            Member memberEntity = optionalMember.get();
//
//            try {
//                log.info("Verify a Refresh Token :");
//                JwtUtil.verify(memberEntity.getRefreshTokenValue());
//                log.info("Queried Access Token is valid. Create a new Access Token :");
//
//                String newAccessToken = jwtUtil.createAccessToken(new CustomUserDetails(memberEntity));
//                response.setHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + newAccessToken);
//            } catch (JWTVerificationException ex) {
//                log.info("JWTVerificationException : " + ex.getMessage());
////                throw new TokenExpiredException(ex.getMessage());
//                this.onUnsuccessfulAuthentication(request, response, new AuthenticationException(ex.getMessage(), ex.getCause()) {});
//                return;
//            }
//
//        }

//        CustomUserDetails customUserDetails = new CustomUserDetails(optionalMember.get());
//
//        Authentication authentication
//                = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        filterChain.doFilter(request, response);
    }

    private Optional<Member> checkMemberEmail(String email) {

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(!optionalMember.isPresent()) {
            throw new UsernameNotFoundException(email);
        }

        return optionalMember;
    }

    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request,
                                                HttpServletResponse response,
                                                AuthenticationException failed) throws IOException {

        // 401(Unauthorized) 상태 발생
        log.info("JwtAuthorizationFilter.onUnsuccessfulAuthentication : 'Expired'");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=utf-8");

        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("timestamp", OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        errorAttributes.put("status", HttpStatus.UNAUTHORIZED);
        errorAttributes.put("exception", failed.getMessage());
        errorAttributes.put("path", request.getRequestURI());

        response.getWriter().println(objectMapper.writeValueAsString(errorAttributes));


    }
}
