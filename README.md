# 도서 판매 Plat-form


## 개요
<br/><br/>


## Technical Specification


**BackEnd**
- Java 11
- Spring boot 2.4.3
- Gradle 6.8.2
- Spring Security 2.4.3
- Spring Hateoas
- Redis
- Lombok
- JPA
- QueryDSL
- jjwt
- MySQL


**Front-end**
- React
- axios
- styled-component
- font Awesome
- react hook
- router
- react strap



## 필요성
<br/><br/>


## Requirements Specification

## - Backend

### [프로젝트 생성 및 DB 연결 설정](https://github.com/wizard0987/bookstore/blob/develop/docs/1_Init.md)

### [회원](https://github.com/wizard0987/bookstore/blob/develop/docs/User.md)
- 가입/탈퇴/로그인/로그아웃/아이디 찾기/비밀번호 찾기
- 회원가입시 미기입 정보 체크 & 이메일 인증 번호 전송 및 확인 & 이메일 중복 확인

### [Security](https://github.com/wizard0987/bookstore/blob/develop/docs/2_Security.md) + [Oauth](https://github.com/localBookStore/bookstore/blob/develop/docs/Oauth.md)
- Oauth 소셜을 통한 로그인 및 회원가입/로그 아웃/회원 탈퇴
- JWT 토큰을 이용한 Authentication & Authorization (access token, refresh token 생성)
- Refresh token 저장을 위한 Redis 저장소 구현

### [관리자](https://github.com/wizard0987/bookstore/blob/develop/docs/Admin.md)
- 회원 리스트 조회/회원 게시글 조회
- 상품 리스트 조회/검색/등록/수정/삭제
- 회원 주문 리스트 조회/주문 취소/주문 

### [Item](https://github.com/wizard0987/bookstore/blob/develop/docs/Item.md)
- 동적 쿼리를 통한 책 검색/ 상세 상품 조회/ 장바구니 담기/ 바로 구매
- 리뷰 리스트/등록/수정/삭제
- 배송/ 장바구니 수량 변경/장바구니 삭제/장바구니 상품리스트/쿠폰 조회

### [Board](https://github.com/wizard0987/bookstore/blob/develop/docs/Board.md)
- 게시글 조회/등록/수정/삭제/추가
- 댓글 등록/수정/삭

## - Frontend



## API 설계도
![API_structure](https://user-images.githubusercontent.com/59079426/110195985-b2792a00-7e84-11eb-8140-3c96399a8fd6.jpg)

<br/>


## 프로토타입

![프로토타입](https://user-images.githubusercontent.com/59079426/114315122-6763ce00-9b38-11eb-9b7e-ea0084453d92.gif)


<br></br>


## 프로젝트 v0.1

![프로젝트v0.1](https://user-images.githubusercontent.com/59079426/114315153-85c9c980-9b38-11eb-82a7-e607fbf53855.gif)
