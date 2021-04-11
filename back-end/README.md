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
'org.springframework.boot:spring-boot-starter-hateoas'              // HATEOAS 추가 : RESTFul API 개발을 위한 라이브러리
'org.springframework.boot:spring-boot-starter-security'             // 스프링 시큐리티
'org.springframework.boot:spring-boot-starter-oauth2-client'        // OAuth2.0 사용 라이브러리
 group: 'io.jsonwebtoken', name: 'jjwt', version: '0.9.1'           // jwt 러이브러리 
 group: 'com.auth0', name: 'java-jwt', version: '3.10.3'            // jwt 러이브러리   
'org.springframework.boot:spring-boot-starter-data-redis            // Redis 추가 : Refresh 토큰 관리를 DB 대신 캐시 메모리에서 관리하기 위함
```


### File Structure (In Progress...)

```markdown
back-end
├── README.md
├── gradlew
├── gradlew.bat
├── .gitignore
├── .gradle
├── gradle
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
└── src
    └── main
    	└── java
    	    └── com.webservice.bookstore	
                ├── BookstoreApplication.java
                ├── domain.entity
                ├── service
                └── web
                    ├── controller
                    └── dto
```


<br/>

## 구현된 기능

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


## 회고
