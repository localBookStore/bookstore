
# Security
**SecurityConfig**

```java
@Log4j2
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .addFilter(corsFilter)
            .addFilterBefore(new JwtAuthenticationFilter(authenticationManager(), jwtUtil, redisUtil),
                    UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JwtAuthorizationFilter(authenticationManager(), jwtUtil, redisUtil, customUserDetailsService),
                    BasicAuthenticationFilter.class)
            .formLogin().disable()
            .httpBasic().disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/board/**", "/api/items/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/coupon/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
                .antMatchers(HttpMethod.POST,"/api/coupon/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/api/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/api/mypage/**", "/api/cart/**", "/api/order/**").access("hasRole('ROLE_USER')")
                .antMatchers("/api/board/**", "/api/items/**", "/logout").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
                .anyRequest().permitAll()
                .and()
            .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    // 특정 권한만 접근할 수 있는 페이지에 대해 로그인 없이 접근하려고 하면 아래 메소드가 호출
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unable to access without login authentication.");
                    }
                })
                .accessDeniedHandler(new AccessDeniedHandler() {
                    // 특정 권한만 접근할 수 있는 페이지에 대해 접근 권한이 없는 (인증된) 계정이 접근하려고 하면 아래 메소드가 호출
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have access.");
                    }
                })
                .and()
            .logout()
                .logoutSuccessHandler(new CustomLogoutSuccessfulHandler(redisUtil))
                .and()
            
				...
    }

}
```


**SessionCreationPolicy.STATELESS**
토큰 인증 방식에서는 세션을 사용하지 않기 때문에 세션 생성 정책을 STATELESS 로 설정합니다. Security 에서 제공하는 기본 로그인과 로그아웃을 비활성화 시켜주었습니다.

**Bean PasswordEncoder**
비밀번호를 안전하게 저장할 수 있도록 비밀번호의 단방향 암호화를 지원하는 PasswordEncoder 인터페이스와 구현체들을 사용하기 위해 빈으로 등록하였습니다.

**http.authorizeRequests.antMatchers()**
특정한 경로에 특정한 권한을 가진 사용자만 접근할 수 있도록 아래의 메소드 사용하였습니다.

**addFilterBefore()**
security에는 Filter들이 기본적으로 여러개 구현되어있다. 자체적으로 Custom Filter를 구현해서 ,  Custom Filter가  상속받는 Filter 이전에 추가하도록 하기 위함이다. 지정된 필터보다 먼저 실행된다.

**exceptionHandling().authenticationEntryPoint()**
특정 권한만 접근할 수 있는 페이지에 대해 로그인 없이 접근하려고 하면 아래 메소드가 호출하도록 한다.

**exceptionHandling(). accessDeniedHandler()**
특정 권한만 접근할 수 있는 페이지에 대해 접근 권한이 없는 (인증된) 계정이 접근하려고 하면 아래 메소드가 호출하도록 한다.


**CorsConfig**

```java
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("http://localhost:3000");   // 모두 IP 주소에 응답을 허용한다
        config.addAllowedHeader("*");   // 모든 Header에 응답을 허용한다.
        config.addExposedHeader("Authorization");
        config.addAllowedMethod("*");   // 모든 Method(GET, POST, PATCH, DELETE) 요청을 허용한다.
        source.registerCorsConfiguration("/api/**", config);
        source.registerCorsConfiguration("/login", config);
        source.registerCorsConfiguration("/logout", config);

        return new CorsFilter(source);

    }
}

```

보통 보안 상의 이슈로 동일 출처를 웹에서는 준수하게 되고,  최초 자원을 요청한 출처 말고 다른 곳으로 요청이 올 경우 금지하게 된다.  이를 방지하기 위해 다른 출처에서도  자원을 요청하여 쓸 수 있게  허용하는 구조를 의미한다.  

**corsFilter**
Bean으로 등록하여, SecurityConfig 클래스에서 필터에 추가하도록 하였다.

**setAllowCredentials(true)**
HTTP Cookie와 HTTP Authentication 정보를 인식할 수 있게 해주는 요청이다. 그래서 Access-Control-Allow-Origin 헤더값는 *가 오면 안되고 , 구체적인 http://localhost:3000 를 명시해주었다.



## JWT Token

JWT는 Json Web Token의 약자로 인증에 필요한 정보들을 암호화시킨 토큰을 의미한다.  새롭게 떠오로는 JWT를 우리 프로젝트에 사용하도록 하였다. 그래서 사용자는 Access Token을 HTTP 헤더에 실어 서버에 보내도록 하는 것이다.

토큰을 만들기 위해서는 기본적으로 3가지가 필요하다. Header (암호화할 방식 alg, 타입 type ) , Payload (서버에 보낼 데이터) , Verify Signature ( Base64 방식으로 인코딩한 Header, Payload 그리고 SECRET KEY를 더한 후 서명) . Header, Payload는 인코딩될뿐, 따로 암호화되지 않습니다. 그래서 누구나 디코딩하여 확인할 수 있습니다.  그래서 Payload에는 유저의 중요한 정보가 들어가면 쉽게 노출되기 때문에 , 중요한 정보는 넣지 않도록 한다.  하지만 Verify Signature는 Secret Key를 알지 못하면 복호화할 수 없다.

Access Token을 통한 인증 방식의 문제는 제 3자에게 탈취당할 경우 보안에 취약하다는 점이 있다. 그래서 보통 Access Token은 유효기간을 짧게 두게 되는데, 이럴 경우 매번 로그인을 자주 해서 불편함을 느낀다. 그래서 Refresh Token이 등장하게 되는데, 이는 Access Token 유효기간을 짧게 유지하도록 하며,  위의 단점을 보완하는 긴 유효시간을 가지는 Refresh Token을 발급해주도록 한다.

**JwtUtil**

```java

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtUtil {

    public String createAccessToken(String email, String nickName, String role) {

        return JWT.create()
                .withSubject(email)
                .withClaim("nickName", nickName)
                .withClaim("role", role)
                .withClaim("exp", Instant.now().getEpochSecond() + 60*10)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

    }

    public String createRefreshToken(String email) {

        return JWT.create()
                .withSubject(email)
                .withClaim("exp", Instant.now().getEpochSecond() + 60*60*24)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

    }

    public VerifyResult verify(String jwtToken) {

        log.info("Input jwtToken : " + jwtToken);

        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                                        .build()
                                        .verify(jwtToken);

            log.info("JWT decoding successful");
            return VerifyResult.builder()
                                .email(decodedJWT.getSubject())
                                .nickName(decodedJWT.getClaim("nickName").asString())
                                .role(decodedJWT.getClaim("role").asString())
                                .result(true)
                                .build();

        } catch (JWTVerificationException e) {
            log.info("JWT has expired");
            DecodedJWT decodedJWT = JWT.decode(jwtToken);

            log.info("JWT has expired");
            return VerifyResult.builder()
                                .email(decodedJWT.getSubject())
                                .nickName(decodedJWT.getClaim("nickName").asString())
                                .role(decodedJWT.getClaim("role").asString())
                                .result(false)
                                .build();
        } catch (NullPointerException e) {  // Redis에 특정 Refresh 토큰이 존재하지 않는 경우(null) 예외 발생
            log.info("JWT does not exist.");
            return VerifyResult.builder()
                                .result(false)
                                .build();
        }

    }

}
```



**createAccessToken()**

위에서 말했다시피, payload는 누구나 디코딩할 수 있기 때문에, 중요한 정보를 넣지 않고 최소한의 정보만 넣도록 한다. 그래서 우리는 프론트 입장에서 필요한 nickName, role, exp(만료시간)로 구성하여 JWT Token을 만들었다.


**createRefreshToken**

검증을 위한 최소한의 것으로 payload(Claim)를 구성하며 ,  긴 유효시간을 가지는 Refresh Token을 만드는 메소드.

**verify**

코드 검증을 위한 로직 코드

- - - -

**VerfiyResult**

검증 결과를 담는 클래스 result 변수를 Boolean 값으로 주어 성공여부를 판단한다.

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyResult {

    private String email;
    private String nickName;
    private String role;
    private boolean result;

}
```


### JWT Token Authentication

JwtAuthenticationFilter 에서는 request 에 접근 토큰(access token) 쿠키가 포함되어 있는지 체크한 후 토큰에 포함된 회원 정보를 이용해 새로운 Authentication 인스턴스를 생성합니다. 

일반적으로 Access Token + Refresh Token 인증과정의 Flow는  
1. 로그인 한 사용자는 access token과 refresh token을 가지고 있다.
2. Access Token이 유효하면 AccessToken내 payload를 읽어 사용자와 관련있는 UserDetail을 생성
3. Access Token이 유효하지 않으면 Refresh Token값을 읽어드림.
4. Refresh Token을 읽어 Access Token을 사용자에게 재생성하고, 요청을 허가시킴.

여기서 보안적인 문제가 드러나게 되는데, 발행된 Access Token 탈취의 위험성을 고려하여 짧은 유효시간 두어, 혹시나 탈취 당하더라도 사용할 수 없도록 한다.
그러나 Refresh Token은 문제가 다르다.  Refresh Token은 유효기간이 길기 때문에 탈취 당한다면 크나큰 보안 약점이 있을뿐더러, 유효기간 안에서 무조건인 요청이 허가된다. 그래서 이 약점을 보완하기 위해

> 우리의 프로젝트는 사용자는 Refresh Token의 요청을 배제시키도록 한다. Access Token으로만 요청을 보내도록 한다. 그리고 Refresh Token을 Redis에 저장시켜두고, Access Token이 만료되더라도, Redis에 있는 Refresh Token과의 검증을 통해서 다시 Access Token을 발급하도록 구성하였다.  

**RedisUtil**

```java
@Component

@RequiredArgsConstructor

public class RedisUtil {

    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, String> redisTemplate;

    public String getData(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void setData(String key, String value, Long time){
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(value.getClass()));
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public void deleteData(String key){
        redisTemplate.delete(key);
    }

    public void setData(String key, String value){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key,value);
    }
}
```

**setData()**
우리의 프로젝트의 ID는 EMAIL이기 때문에 , redis에 이메일을 key,  refresh token을 value값으로 주어 저장을 하도록 한다. 그리고 뒤에 시간은 redis 저장소에서 refresh token의 유효시간이다.



**JwtAuthenticationFilter**

```java
@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private ObjectMapper objectMapper = new ObjectMapper();

/*
 * 해당 필터에서 인증 프로세스 이전에 요청에서 사용자 정보를 가져와서
 * Authentication 객체를 인증 프로세스 객체에게 전달하는 역할
 */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.info("JwtAuthenticationFilter.attemptAuthentication : Attempting Login...");

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            Member member = objectMapper.readValue(request.getInputStream(), Member.class);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 권한(Role) 확인을 위해 세션에 임시로 저장하여 un/successfulAuthentication 메소드에 넘겨줌
            return authentication;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String writeTimeNow() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {

        log.info("JwtAuthenticationFilter.successfulAuthentication :");

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = customUserDetails.getMember().getEmail();
        String nickName = customUserDetails.getMember().getNickName();
        String role = String.valueOf(customUserDetails.getMember().getRole());

        if(customUserDetails.isEnabled()) {
            // 새로운 Refresh 토큰 생서 및 Redis에 저장
            redisUtil.setData(email, jwtUtil.createRefreshToken(email), 60L*60*24);

            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json;charset=utf-8");
            response.setHeader(JwtProperties.HEADER_STRING,
                    JwtProperties.TOKEN_PREFIX + jwtUtil.createAccessToken(email, nickName, role));

            Map<String, Object> resultAttributes = new HashMap<>();
            resultAttributes.put("timestamp", writeTimeNow());
            resultAttributes.put("status", HttpStatus.OK);
            resultAttributes.put("message", "Authentication completed (Default)");
            resultAttributes.put("path", request.getRequestURI());

            response.getWriter().println(objectMapper.writeValueAsString(resultAttributes));

        } else {
            response.setStatus(HttpStatus.LOCKED.value()); // 423 응답값
            response.setContentType("application/json;charset=utf-8");

            Map<String, Object> resultAttributes = new HashMap<>();
            resultAttributes.put("timestamp", writeTimeNow());
            resultAttributes.put("status", HttpStatus.LOCKED);
            resultAttributes.put("message", "This Email is locked (Default)");
            resultAttributes.put("path", request.getRequestURI());

           response.getWriter().write(objectMapper.writeValueAsString(resultAttributes)); response 바디에 json 으로 담기.

            SecurityContextHolder.getContext().setAuthentication(null);
        }

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        // 401(Unauthorized) 상태 발생
        log.info("JwtAuthenticationFilter.unsuccessfulAuthentication : 'Unauthorized'");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=utf-8");

        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("timestamp", OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        errorAttributes.put("status", HttpStatus.UNAUTHORIZED);
        errorAttributes.put("exception", failed.getMessage());
        errorAttributes.put("path", request.getRequestURI());

        
  response.getWriter().write(objectMapper.writeValueAsString(errorAttributes)); // write response body

    }

}
```


**attemptAuthentication()**
로그인 시도시 진행되는 메소드로, 해당 필터에서 인증 프로세스 이전에 요청에서 사용자 정보를 가져와서 Authentication 객체를 인증 프로세스 객체에게 전달하는 역할이다.


**successfulAuthentication()**
성공적으로 인증을 하게 되면, 해당 메소드로 진입하게 된다. 그래서  `authentication` 객체로부터,  해당 유저 정보를 읽어들여서  `isEnabled()`를 통한 계정 활성화를 체크한다.  맞다면 해당 사용자에게 refresh token과 access token을 발급하게 된다. 그리고 refresh token은 보내지 않고 **Redis** 저장소에 저장하게 되고 , access token을 발급하도록 하였다.  맞지 않다면 , 계정이 잠겨있음을 응답으로 보내도록 하였다.

**unsuccessfulAuthentication()**
인증 실패 , 진입하게 되는 메소드. `HttpStatus.UNAUTHORIZED` 을 응답으로 보내도록 한다.




**JwtAuthorizationFilter**

```java

@Log4j2

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private JwtUtil jwtUtil;
    private RedisUtil redisUtil;
    private CustomUserDetailsService customUserDetailsService;
    private ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  JwtUtil jwtUtil, RedisUtil redisUtil,
                                  CustomUserDetailsService customUserDetailsService) {

        super(authenticationManager);
        this.jwtUtil    = jwtUtil;
        this.redisUtil      = redisUtil;
        this.customUserDetailsService = customUserDetailsService;

    }



    private void saveAuthSecuritySession(VerifyResult verifyResult) {

        log.info("Query Member By JWT Subject(email) :");

        CustomUserDetails customUserDetails
                = (CustomUserDetails) customUserDetailsService.loadUserByUsername(verifyResult.getEmail());

	Authentication auth
                = new UsernamePasswordAuthenticationToken(customUserDetails ,null, customUserDetails.getAuthorities());


        log.info("Save Authentication in SecuritySession :");

        SecurityContextHolder.getContext().setAuthentication(auth);

    }



    @Override

    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        log.info("JwtAuthorizationFilter.doFilterInternal :");

        String authorizationValue = request.getHeader(JwtProperties.HEADER_STRING);  // jwt 토큰 값

        // JWT 토큰 값이 없거나 'Bearer ' 문자열로 시작하지 않는다면 다음 필터로 넘겨줌

        if (StringUtils.isEmpty(authorizationValue) || !authorizationValue.startsWith(JwtProperties.TOKEN_PREFIX)) {
            log.info("No JWT, JWT structure issue :");
            filterChain.doFilter(request, response);
            return;

        }

        String jwtToken = authorizationValue.substring(JwtProperties.TOKEN_PREFIX.length());

        log.info("Verify the input Access Token :");
        VerifyResult verifyResult = jwtUtil.verify(jwtToken);
        if(verifyResult.isResult()) {

            log.info("The input Access Token is valid! :");
            saveAuthSecuritySession(verifyResult);


        } else {

            log.info("The input Access Token is invalid... :");

            String email = verifyResult.getEmail();
            String nickName = verifyResult.getNickName();
            String role = verifyResult.getRole();

            String refreshToken = redisUtil.getData(email);
            log.info("Saved Refresh Token in Redis : {}", refreshToken);

            log.info("Verify the Refresh Token :");

            VerifyResult refreshVerify = jwtUtil.verify(refreshToken);
            if(refreshVerify.isResult()) {

                log.info("The Refresh Token is valid! Create new Access Token :");
                String newAccessToken = jwtUtil.createAccessToken(refreshVerify.getEmail(), nickName, role);
		
                response.setHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + newAccessToken);
                saveAuthSecuritySession(verifyResult);

            } else {

                log.info("The Refresh Token is invalid... :");
                redisUtil.deleteData(email);
                RuntimeException e = new TokenExpiredException("The Refresh Token is invalid");
                this.onUnsuccessfulAuthentication(
                            request, response, new AuthenticationException(e.getMessage(), e.getCause()) {}
                        );
                return;
            }
        }

        filterChain.doFilter(request,response);

    }



    @Override

    protected void onUnsuccessfulAuthentication(HttpServletRequest request,
                                                HttpServletResponse response,
                                                AuthenticationException failed) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(null);

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
```

**doFilterInternal** 
JWT 토큰을 받고, 내어보내는 endpoint 이다. JWT 토큰은 HTTP 구조 중 헤더에 있기 때문에 헤더에서 JWT 토큰을 받아 검증하고, 인증이 되면 SecurityContext에 보관하여 보안을 통과하게 된다. 검증에서 실패하게 된다면, 즉 유효 시간이 자나게 되면,  Access Token에 담겨진 email로 Refresh token을 얻어서 그 Refresh token을 다시 검증하는 로직이다. 그리고 검증하고 인증이 되면, 새로운 Access Token을 만들어서 응답 요청 헤더에 실어주도록 한다.  Refresh token에서 검증 실패한 경우는,  redis에서 해당 Refresh token 삭제 후, 인증 실패시 진입하게 되는  `onUnsuccessfulAuthentication`  메소드로 넘겨주게 된다.

**onUnsuccessfulAuthentication**
에러를 받아서, `HttpStatus.UNAUTHORIZED`  을 응답으로 보내도록 한다.
