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

### [프로젝트 생성 및 DB 연결 설정](https://github.com/wizard0987/bookstore/blob/develop/docs/1_Init.md)

### [회원](https://github.com/wizard0987/bookstore/blob/develop/docs/User.md)
- 가입/탈퇴/로그인/로그아웃/아이디 찾기/비밀번호 찾기
- 회원가입시 미기입 정보 체크 & 이메일 인증 번호 전송 및 확인 & 이메일 중복 확인

### [Security](https://github.com/wizard0987/bookstore/blob/develop/docs/2_Security.md) + [Oauth](https://github.com/localBookStore/bookstore/blob/develop/docs/Oauth.md)
- Oauth 소셜을 통한 로그인 및 회원가입/로그 아웃/회원 탈퇴
- JWT 토큰을 이용한 Authentication & Authorization (access token, refresh token 생성)
- Refresh token 저장을 위한 Redis 저장소 구현

### [Item](https://github.com/wizard0987/bookstore/blob/develop/docs/Item.md)
- 동적 쿼리를 통한 책 검색/ 상세 상품 조회/ 장바구니 담기/ 바로 구매
- 리뷰 리스트/등록/수정/삭제
- 배송/ 장바구니 수량 변경/장바구니 삭제/장바구니 상품리스트



## API 설계도
![API_structure](https://user-images.githubusercontent.com/59079426/110195985-b2792a00-7e84-11eb-8140-3c96399a8fd6.jpg)

<br/>


## 프로토타입
<br/>

> **카카오 오븐 사용 <br/>
> 각각의 이미지는 prototype-image 폴더에 저장<br/>
> [프로토타입 페이지](https://ovenapp.io/view/IsuAqqRNVhCdVOBfCScCSPSGLUS1NAlB/3cieS)**
   
<br/>   
   
  
- [메인 페이지](#메인-페이지)
- [메인 페이지 카테고리](#메인-페이지-카테고리)
- [책 상세 페이지](#책-상세-페이지)
- [로그인 페이지](#로그인-페이지)
- [회원가입 페이지](#회원가입-페이지)
- [커뮤니티 페이지](#커뮤니티-페이지)
- [게시글 상세 페이지](#게시글-상세-페이지)
- [게시글 수정 페이지](#게시글-수정-페이지)
- [도서 목록 페이지](#도서-목록-페이지)
- [장바구니 페이지](#장바구니-페이지)
- [배송 페이지](#배송-페이지)
- [마이페이지_회원정보](#마이페이지_회원정보)
- [마이페이지_주문내역](#마이페이지_주문내역)
- [마이페이지_쓴글보기](#마이페이지_쓴글보기)
- [마이페이지_회원탈퇴](#마이페이지_회원탈퇴)
- [관리자페이지_회원리스트](#관리자페이지_회원리스트)
- [관리자페이지_상품리스트](#관리자페이지_상품리스트)
- [관리자페이지_주문리스트](#관리자페이지_주문리스트)

<br/><br/>

### 메인 페이지
---
<img src="https://user-images.githubusercontent.com/56857925/108664347-e879ee00-7515-11eb-8f36-2b479cd8a1cd.jpg" width="500">

1. 카테고리 바는 국내 도서, 외국 도서, BEST, NEW, 커뮤니티로 구성되어 있다.
2. 카테고리 하단에 있는 도서 이미지로 애니매이션 효과를 주어서 자동적으로 좌우로 이동할 수 있게 한다.
3. 우리의 PICK ! 컴포넌트의 경우 저희들이 임의로 지정한 책들로 구성하도록 한다. 마찬가지로 애니메이션 효과를 추가
4. 이달의 도서 컴포넌트의 경우 한달 간 조회수가 가장 많은 도서로 구성하도록 한다. 마찬가지로 애니메이션 효과를 추가
5. 우측 상단에는 로그인 / 회원가입으로 구성하였으며, 로그인 후는 마이페이지 / 장바구니 / 로그아웃으로 바꾸도록 한다.


<br/><br/>  
  

  
### 메인 페이지 카테고리
---    
<img src="https://user-images.githubusercontent.com/56857925/108665452-4f98a200-7518-11eb-860b-74b51c8415b1.jpg" width="500">

1. 카테고리 바에 마우스를 올려 놓기만 해도 카테고리 별 메뉴를 띄우도록 하였다.
2. 카테고리에 마우스를 올려 놓을시 바디에 있는 내용들이 밑으로 내려가도록 하는게 아니라, 위에 덮어씌우도록 한다.
3. 카테고리 메뉴를 클릭하면 해당 카테고리로 이동하도록 한다.
4. 상위 카테고리 안에 있는 하위 카테고리에 마우스를 올려놓게 되면 그 해당 하위 카테고리에 추천 알고리즘을 통한 추천작을 구성하도록 한다.


<br/><br/>


### 책 상세 페이지
---    
<img src="https://user-images.githubusercontent.com/56857925/108666352-401a5880-751a-11eb-84d6-0d6947fb7106.jpg" width="500">

1. 책 상세 페이지는 장바구니, 바로 구매 버튼과 책에 대한 상세 설명, 하단에는 댓글로 구성되도록 한다.
2. 장바구니 , 구매 버튼을 누를시 해당 페이지로 이동하게 된다.
3. 댓글은 대댓글로 구성하도록 하며, 비동기 통신을 할 예정.


<br/><br/>


### 로그인 페이지
---  
<img src="https://user-images.githubusercontent.com/56857925/108666587-cc2c8000-751a-11eb-92ad-f2634cab0dba.jpg" width="500">

1. 로그인 버튼, 소셜로그인 버튼,  회원가입, 아이디 비밀번호 찾기 버튼으로 구성되어 있다.
2. spring oauth를 이용한 소셜 로그인 (Google)이 있으며, 추후에 더 추가할 예정이다.


<br/><br/>


### 회원가입 페이지
---

<img src="https://user-images.githubusercontent.com/56857925/108666793-4ceb7c00-751b-11eb-9c1f-0f389dd87725.jpg" width="500">

1. 아이디, 패스워드, 패스워드 확인, 이메일, 주소 찾기 창으로 이루어져 있다.
2. 아이디는 중복화인 기능을 추가하였다.
3. 패스워드는 특정 비밀번호 조합만 가능하도록 하였으며, 확인창도 구성하였고 비동기 통신으로 바로 확인할 수 있게끔 한다.
4. 이메일도 이메일 조합만 허락하도록 한다.
5. 주소 찾기는 api를 이용한 주소 찾기.


<br/><br/>

### 커뮤니티 페이지
---

<img src="https://user-images.githubusercontent.com/56857925/108670522-5af0cb00-7522-11eb-83aa-144fbc427113.jpg" width="500">

1. 게시판 형식 (분류, 제목, 조회수, 작성시간)으로 구성.
2. 로그인 유무와 상관없이 게시글 보는 것이 가능하다.
3. 이 커뮤니티 사이트는 기본적으로 고객들이 서로 의견을 주고 받으며, 중고 책들을 사고 팔 수 있는 공간이다.


<br/><br/>
### 게시글 상세 페이지
---
<img src="https://user-images.githubusercontent.com/56857925/108671114-5b3d9600-7523-11eb-9460-a2d1b37d56a4.jpg" width="500">

1. 게시글 제목, 게시글 상세 내용 작성( 사진 첨부기능 ), 수정 버튼, 삭제 버튼, 댓글 기능으로 구성.
2. 해당 게시글의 수정과 삭제는 해당 게시글을 작성한 사람만 가능하다.
3. 수정 버튼을 누를시 게시글 수정 페이지로 이동, 삭제 기능은 삭제 확인 모달을 통한 2차 확인 후 게시글 페이지로 리다이렉트 된다.
4. 댓글 기능은 위의 작성한 댓글 기능과 동일하다.


<br/><br/>

### 게시글 수정 페이지 
---
<img src="https://user-images.githubusercontent.com/56857925/108680352-1d476e80-7531-11eb-83b5-5c6884211ad1.jpg" width="500">
<br/><br/>


### 도서 목록 페이지 
---
<img src="https://user-images.githubusercontent.com/56857925/108680555-58e23880-7531-11eb-8ff6-e7120d14be21.jpg" width="500">

<br/><br/>
### 장바구니 페이지 
---
<img src="https://user-images.githubusercontent.com/56857925/108680660-7ca57e80-7531-11eb-8b83-adef9d6179b7.jpg" width="500">

<br/><br/>
### 배송 페이지 
---
<img src="https://user-images.githubusercontent.com/56857925/108680210-eb360c80-7530-11eb-90b4-65c8caebf663.jpeg" width="500">


<br/><br/>
### 마이페이지_회원정보
---
<img src="https://user-images.githubusercontent.com/56857925/108680853-b9717580-7531-11eb-978c-fd896b4769b6.jpg" width="500">
<br/><br/>


### 마이페이지_주문내역
---
<img src="https://user-images.githubusercontent.com/56857925/108680959-da39cb00-7531-11eb-8097-ec126a722f3c.jpg" width="500">
<br/><br/>


### 마이페이지_쓴글보기
---
<img src="https://user-images.githubusercontent.com/56857925/108681067-fb9ab700-7531-11eb-8f60-b7ad05cefdca.jpg" width="500">

<br/><br/>

### 마이페이지_회원탈퇴
---
<img src="https://user-images.githubusercontent.com/56857925/108681203-3270cd00-7532-11eb-8e12-0b160f04c046.jpg" width="500">

<br/><br/>
### 관리자페이지_회원리스트
---
<img src="https://user-images.githubusercontent.com/56857925/108681335-62b86b80-7532-11eb-9f6f-35e7bcb106c4.jpg" width="500">

<br/><br/>

### 관리자페이지_상품리스트
---
<img src="https://user-images.githubusercontent.com/56857925/108681503-909db000-7532-11eb-9766-236ffe082f93.jpg" width="500">


<br/><br/>
### 관리자페이지_주문리스트
---
<img src="https://user-images.githubusercontent.com/56857925/108681603-b034d880-7532-11eb-8ac4-a9688b140672.jpg" width="500">
