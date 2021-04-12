# BackEnd

## 프로젝트 생성
https://start.spring.io 에서 프로젝트를 생성합니다.
  - 이클립스 및 Intellij 설치방법 : https://www.baeldung.com/lombok-ide

## Build and Run
위에서 소개한 IDE에 해당 프로젝트를 git clone한 후 Build/Run하거나, 아래에 소개할 terminal에 직접 명령어를 작성하여 이용하면 됩니다.

##### Build : 
해당 프로젝트 내 gradlew 파일이 위치한 경로('back-end/')로 이동하여 다음 명령어를 수행합니다.
```bash
$ ./gradlew build
```

##### Run :
위 명령어를 실행하면 'back-end/build/libs/' 경로에 jar 파일이 생성됩니다.<br/>
java 명령어로 해당 jar 파일을 실행하면 http://localhost:8080로 접근할 수 있습니다.
```bash
$ java -jar build/libs/[파일명].jar 또는
$ java -jar build/libs/*.jar
```


### Dependencies

```bash
'spring-boot-starter-web'                                       
'spring-boot-starter-test'          
'spring-boot-devtools'              
'spring-boot-starter-data-jpa'                                      // spring data jpa 사용을 위한 라이브러리
'spring-boot-starter-validation'                                    // validation 체크를 위한 라이브러리
'mysql-connector-java'              
'org.projectlombok:lombok'                                          // 롬복
'org.springframework.boot:spring-boot-starter-hateoas'              // HATEOAS 추가 : Restful API 개발을 위한 라이브러리
'org.springframework.boot:spring-boot-starter-security'             // 스프링 시큐리티
'org.springframework.boot:spring-boot-starter-oauth2-client'        // OAuth2.0 사용 라이브러리
 group: 'io.jsonwebtoken', name: 'jjwt', version: '0.9.1'           // jwt 러이브러리 
 group: 'com.auth0', name: 'java-jwt', version: '3.10.3'            // jwt 러이브러리   
'org.springframework.boot:spring-boot-starter-data-redis            // Redis 추가 : Refresh 토큰 관리를 DB 대신 캐시 메모리에서 관리하기 위함
'org.springframework.boot:spring-boot-starter-mail'                 // 이메일 인증 : 위한 이메일 관련 객체 사용
```


<br/>

## 🔧구현된 기능
    
```markdown
프로젝트 전반에 걸쳐 , 프론트엔드와 백엔드의 독립적인 개발 , Restful한 API를 만들고자 Spring Hatoas를 사용하여 개발하였다.
```
 
### Spring Security + Oauth2.0 + JWT
- Oauth 소셜을 통한 로그인 및 회원가입/로그 아웃/회원 탈퇴
- JWT 토큰을 이용한 Authentication & Authorization (access token, refresh token 생성)
- Refresh token 저장을 위한 Redis 저장소 구현 

### 회원
- 가입/탈퇴/로그인/로그아웃/아이디 찾기/비밀번호 찾기(임시 비밀번호 발급)
- 회원가입시 미기입 정보 체크 & 이메일 인증 번호 전송 및 확인 & 이메일 중복 확인

### 관리자
- 회원 리스트 조회/회원 게시글 조회
- 상품 리스트 조회/검색/등록/수정/삭제
- 회원 주문 리스트 조회/주문 취소/주문

### Item 
- 동적 쿼리를 통한 책 검색/상세 상품 조회/장바구니 담기/바로 구매
- 리뷰 리스트/등록/수정/삭제

### Order
- 주문 생성(상품,쿠폰, 배송 정보 포함)

### Cart
- 장바구니 수량 변경/장바구니 삭제
- 장바구니 상품리스트/멤버별 쿠폰 조회

### Coupon
- 쿠폰 조회
- 쿠폰 발급

### Community
- 게시글 조회/등록/수정/삭제/추가
- 댓글 등록/수정/삭제

<br/>


### File Structure (In Progress...)

```markdown
back-end
├── .gitignore       
├── .gradle
├── build
│   ├── classes
│   ├── generated
│   ├── libs
│   │   └── bookstore-0.0.1-SNAPSHOT.jar
│   ├── reports
│   ├── resources
│   ├── test-result
│   ├── bootJarMainClassName
│   └── bootRunMainClassName
├── gradle
├── gradlew
├── gradlew.bat
├── README.md
├── settings.gradle
└── src
    ├── main
    │	├── java
    │	│   └── com.webservice.bookstore	
    │   │       ├── config
    │   │       │   ├── security
    │   │       │   │   ├── auth
    │   │       │   │   │   ├── CustomUserDetails.java         # Security에서 (일반/OAuth2) 회원 정보를 관리하는 UserDetails 인터페이스 구현체
    │   │       │   │   │   └── CustomUserDetailsService.java  # DB에서 회원 정보를 가져오는 역할하는 UserDetailsService 인터페이스 구현체
    │   │       │   │   ├── jwt 
    │   │       │   │   │   ├── JwtAuthenticationFilter.java   # Login 요청 시 인증 절차를 처리하는 Filter
    │   │       │   │   │   ├── JwtAuthorizationFilter.java    # api 리소스 요청에 대한 JWT의 권한 및 토큰 유효여부 검증하는 Filter
    │   │       │   │   │   └── JwtUtil.java                   # JWT Access/Refresh 토큰 생성 및 검증 처리 담당 클래스
    │   │       │   │   ├── oauth2
    │   │       │   │   │   ├── handler
    │   │       │   │   │   ├── provider
    │   │       │   │   │   │   ├── GoogleOAuth2UserInfo.java  # Google/Kakao/Naver부터 회원 정보를 추출하는 OAuth2UserInfo 인터페이스 구현체
    │   │       │   │   │   │   ├── KakaoOAuth2UserInfo.java
    │   │       │   │   │   │   ├── NaverOAuth2UserInfo.java
    │   │       │   │   │   │   └── OAuth2UserInfo.java        # OAuth2 Provider로부터 받은 정보를 매칭할 인터페이스
    │   │       │   │   │   └── CustomOAuth2UserService.java   # OAuth2 회원 정보 기반으로 회원가입 및 정보 업데이트, JWT 토큰 제공 담당 Service
    │   │       │   │   ├── CorsConfig.java       # Spring Security에서 제공하는 CORS 정책 제어 기본 설정
    │   │       │   │   └── SecurityConfig.java   # Spring Security를 이용해 인증 및 인가 처리 여부 등을 기본 설정
    │   │       │   ├── QuerydslConfiguration.java  # 프로젝트 내 어디서든 QueryDSL을 사용할 수 있도록 기본 설정
    │   │       │   └── RedisConfig.java            # JWT Refresh 토큰을 관리하는 Redis 기본 세팅
    │   │       ├── domain.entity   # 실제 DB의 각 테이블과 매칭될 클래스(Entity) 및 DB에 직접 접근하는 Repository 컴포넌트 관리 패키지
    │   │       │   ├── board                                 
    │   │       │   ├── cart
    │   │       │   ├── category
    │   │       │   ├── coupon
    │   │       │   ├── delivery
    │   │       │   ├── image
    │   │       │   ├── item
    │   │       │   ├── member
    │   │       │   ├── order
    │   │       │   ├── orderItem
    │   │       │   ├── reply
    │   │       │   ├── review
    │   │       │   └── BaseTimeEntity.java   # 각 Entity의 생성/수정 날짜시간을 자동으로 관리해주는 추상화 클래스
    │   │       ├── exception
    │   │       │   └── CommonExceptionAdvice.java  # Spring Controller에서 발생하는 예외 처리 담당 클래스
    │   │       ├── service   # Controller/Repository로부터 전달받은 데이터를 가공하는 Service 컴포넌트를 관리하는 패키지
    │   │       ├── util
    │   │       │   ├── EmailUtil   # 회원가입 인증코드, 비밀번호 찾기를 위해 실제로 이메일 전송을 담당하는 클래스 
    │   │       │   └── RedisUtil   # 인증코드 임시 관리, JWT Refresh 토큰을 관리하는 Redis 저장소 관리를 담당하는 클래스                                                                     
    │   │       ├── web
    │   │       │   ├── controller  # Client의 요청을 처리하는 Controller 컴포넌트 관리 패키지
    │   │       │   ├── dto         # 계층 간 데이터 교환을 위한 객체 관리 패키지
    │   │       │   └── resource    # HATEOAS 원칙을 따라 리소스에 접근할 수 있는 링크 레퍼런스인 Links를 제공하는 클래스 관리 패키지
    │   │       └── BookstoreApplication.java   # 해당 백엔드 프로젝트의 시작 클래스
    │   └ resources
    │	    ├ application.properties         # Spring Boot 외부 설정 파일 
    │	    └ application-oauth.properties   # OAuth2 로그인 서비스 등록을 위한 설정 파일       
    └ test   # 테스트를 위한 자바 코드와 관련 리소스를 보관
```


<br/>


## 🤜🤛 회고

**항진** : 많은 경험이 되는 프로젝트였다. 다른분들과 협업을 통해 제가 모르고 있었던 점도 배우고 무의식적으로 놓치고 있는 부분에 대해서도 다시 한번 생각하는 계기가 되는 프로젝트인다.프로젝트 초기에 채팅 기능까지 구현해보고 싶었는데. 현시점에서 구현을 아직 안했다. 물론 추후에 기능 추가할 생각이다. 더불어 배포에 대한 어려움을 느낀 프로젝트이다. 완성을 향해 달려가고 있지만, 배포가 이렇게 어려울 줄은 생각도 못했다. 생각도 못한 곳에서 에러도 발생하고. 현재 진행중인 프로젝트라서 여기까지만 적을 생각이다. 추후에 프로젝트에 대한 회고를 추가하겠다.

**지원** : 처음으로 프론트엔드 개발자와 함께 진행한 프로젝트인 만큼 나의 모자란 점과 새로운 지식을 터득하며 여러 시행착오를 겪으면 바쁜 일정을 보냈었다. 지금의 팀원들과 함께 활동하면서 서로 의지하며 목표를 이루어나가는 개발 공동체의 매력을 새삼 다시 느낄 수 있었으며, 그 안에서 개발에 대한 열정과 활력을 다시 한번 느낄 수 있었던 값진 시간이었다.
**재섭** : 개인프로젝트만 진행했었지만 처음으로 팀원과 함께하는 첫번째 프로젝트였습니다. 데이터베이스 설계에대해 더 많은것을 공부할 수 있는 계기가 되었고 댓글과 대댓글을 스스로 설계하면서 프로젝트 개발에서 DFS라는 알고리즘을 사용하여 위상정렬과 삭제를 구현할 수 있었다는것이 가장 인상깊었다. 아쉬운점은 전자결제 시스템을 이용하여 중고나라와 같은 전자결제시스템을 만들어 보고싶고 채팅을 구현하여 더많은 사람들이 안전하게 시스템을 이용할수있도록 시스템을 더욱더 개선하고자한다