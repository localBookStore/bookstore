# 🖼 Front End



### ⚙️ Usage

```markdown
# react dependency & library 설치
npm install

# react server 실행 (3000 포트)
npm start
```



###  ⛪️ Library

```bash
'react-router-dom'        # Apply Domain Routing
'axios'                   # Asynchronous Request
'react-bootstrap'         # Apply Carousel & modal
'react-slick'             # For a apply Multi Carousel (Like Netflix...)
'slick-carousel'          # react-slcik CSS
'fontawesome 3 Pakage'    # for react UI font
  '@fortawesome/fontawesome-svg-core'
  '@fortawesome/free-solid-svg-icons'
  '@fortawesome/react-fontawesome'
'styled-components'       # apply for CSS in js
'poished'                 # interactive utill funcion
'qs'                      # using query string (사용x)
'react-cookie'            # Using Cookie in Browser
'react-hook-form'         # for Using user input form
'jwt-decode'              # Do jwt decode library (using find user info)
'react-google-login'      # for social login of google
'react-paginate'          # apply paginate of items
'react-rating-starts-component'   # apply rating star for react
'react-icons'             # for react web icon
'react-material-ui'	      # using for various Form
'react-sticky-el'         # for a stick-component
```



## 🛠  Core Feature

- #### [Common Feature]

  - Front - back 관계에서 원활한 요청과 응답을 해결하기 위해 비동기 요청(`Axios`) 수행
  - Styled-Components 라이브러리를 이용하였기에, 기존 css를 사용했을 때의 단점인 복잡한 파일구조를 해소하며 각 컴포넌트의 독립적인 네이밍을 구현
  - React-Hook을 사용하여, 상태관리를 위한 useState, 라이프사이클로 응용되는 useEffect 이용함으로서 함수형 컴포넌트로의 환경을 구축
  - 웹 브라우저의 cookie를 이용하여 JWT 토큰을 저장하고, 이를 이용하여 로그인 여부를 판단하고 디코딩을 통한 최소한의 정보를 이용하여 검증 수행
  - 회원가입시 정규표현식을 통한 검증과 함께 중복확인, 인증번호 등의 순차적인 프로세스가 진행될 수 있도록 JSX 제어문( &&, 삼항연산자 )을 활용하였고, 특히 인증번호를 송신하는 경우, Spinner를 보여주며 시간상의 공백을 줄여보려 노력..

- #### [Header Page]

  - `검색` : 사용자의 상태관리를 위한 useState를 사용하여 사용자의 입력값을 받고, Enter 혹은 Click 이벤트를 통해 결과 페이지로 전환
  - `장르 태그 MouseOver`: 사용자가 '장르'버튼에 마우스를 올릴 시에, state의 props를 받아 새로운 컴포넌트(`CategoryHoverDetail.js`)를 시각화 하는 기능을 구현

- #### [MainPage]

  - `커뮤니티의 수정 삭제 검증` : 웹 브라우저의 cookie를 디코딩하여 게시글의 동일인 여부를 판단하고 해당 기능 버튼을 시각화 과정
  - `커뮤니티 댓글 및 댓댓글` :  Back-end로의 댓글요청에 대한 응답데이터를 통해 댓글의 깊이를 props로 받아, 단위마다의 들여쓰기 기능 수행
  - `상세 도서의 장바구니/바로구매`: 장바구니 버튼을 누르면 요청을 통해 데이터베이스에 해당 아이템을 저장, 바로구매시 장바구니 기능과 함께 장바구니로의 페이지 이동
  - `상세 도서의 리뷰` : 리뷰를 위한 별점기능 추가, Hover를 통해 별점시각화, Click을 통해 선택을 이용
  - `홈 페이지의 랜덤 이미지 슬라이드` : 라이브러리를 이용하여 Multi Carousel를 구현하면서 응답으로 받은 랜덤 이미지를 시각화 / 커스텀한 Next / Prev 화살표 버튼을 통해 이미지 제어 슬라이드 이용

- #### [UserPage]

  - `일반 유저의 장바구니`: 장바구니의 내용을 state로 저장하고, 체크박스를 이용한 아이템 선택/해제, 아이템의 수량변경, 장바구니 아이템삭제, 적용할 쿠폰 선택, 전체 비용 계산 기능을 수행 
  - `일반유저의 주문내역`: 사용자가 주문한 내역을 보여줄 수 있도록 하며, 배송시작전 배송 취소가능, 또한 상세보기를 통해 사용자가 구매한 아이템들과 배송상태를 Progress Bar로 시각화
  - `일반유저페이지의 중첩라우팅`: 마이페이지 내에서도 개인정보/ 내 글보기/ 주문내역 기능을 이용할 수 있도록 중첩라우팅 구현

- #### [AdminPage]

  - `관리자의 상품관리` : 관리자는 사용자가 보는 전체 아이템을 관리하기 떄문에 많은 데이터를 분할적으로 볼 수 있도록 아이템 리스트의 Pagination을 적용
  - `관리자페이지의 중첩라우팅`: 관리자 페이지 내에서도 유저정보/ 물품관리/ 주문관리를 이용할 수 있도록 중첩라우팅 구현



### 📦 File Structure (In Progress...)

```bash
books
├── README.md
├── node_modules
├── package-lock.json
├── package.json
├── .gitignore
├── public
│ 	├── favicon.ico
│ 	└── index.html
└── src
├── App.js
├── icons
│	├── books.jpg
│ 	└── open-book.jpg
├── index.css
├── index.js
├── components
│	├── HeaderPage
│	│	├── CategoryBar.js			# 장르 / 베스트 / 신간 / 커뮤니티 별로 Link되는 컴포넌트
│	│	├── CategoryHoverDetail.js		# 장르 버튼에 Mouse Over시 display되는 컴포넌트
│	│	├── Header.js				# Header Container
│	│	└── SearchBar.js			# 제목별 검색기능
│	├── MainPage
│	│	├── BookListPage
│	│	│	├── BestBookList.js		# 베스트 아이템 리스트
│	│	│	├── NewBookList.js		# 신간 아이템 리스트
│	│	│	└── SearchBookList.js		# 검색 결과 아이템 리스트
│	│	├── CommunityPage
│	│	│	├── Community.js		# 전체 커뮤니티 컴포넌트
│	│	│	├── CommunityDetail.js		# 상세 커뮤니티 게시글 + 댓글 
│	│	│	├── ArticleDetail.js		# 상세 커뮤니티 게시글
│	│	│	├── CommentDetail.js		# 상세 커뮤니티 댓글
│	│	│	├── EachComment.js		# 각 댓글 컴포넌트
│	│	│	├── CommunityRegister.js	# 게시글 등록
│	│	│	└── CommunityUpdate.js		# 게시글 수정(업데이트)
│	│	├── DetailPage
│	│	│	├── ItemDetail.js		# 상세 도서 컴포넌트
│	│	│	├── TopDetail.js		# 책의 이미지 / 제목 / 저자 / 출판사 / 가격 / 재고 + 장바구니 / 바로구매
│	│	│	├── MidDetail.js		# 책의 설명
│	│	│	├── ReviewList.js		# 책을 구매한 유저의 댓글 및 평점 리스트
│	│	│	└── Review.js			# 각 댓글 컴포넌트
│	│	├── HomePage
│	│	│	├── Home.js			# 메인 홈 화면
│	│	│	├── SlideItem.js		# 하나의 도서에 대한 슬라이드
│	│	│	├── PickItem.js			# 랜덤 4개의 도서에 대한 멀티 슬라이드
│	│	│	├── MonthBooks.js		# 랜덤 4개의 도서에 대한 멀티 슬라이드
│	│	│	└── CustomArrow
│	│	│		├── NextArrow.css	# 다음 슬라이드 제어 버튼 CSS		
│	│	│		├── NextArrow.js	# 다음 슬라이드 제어 버튼
│	│	│		├── PrevArrow.css	# 이전 슬라이드 제어 버튼 CSS
│	│	│		└── prevArrow.js	# 이전 슬라이드 제어 버튼
│	│	└── UserPage
│	│		├── Admin
│	│		│	├── AllOrderListComponent
│	│		│	│	├── AllOrderList.js	# 모든 유저 목록
│	│		│	│	├── EachUserOrder.js	# 각 유저의 주문 내용
│	│		│	│	└── UserOrder.js	# 각 유저의 상세 주문 내용
│	│		│	├── AdminPage.js		# 관리자 컴포넌트
│	│		│	├── AllItemList.js		# 등록된 전체 상품 리스트
│	│		│	├── EachItemList.js		# 각 상품 제어 컴포넌트
│	│		│	├── PostCoupon.js		# 쿠폰 등록 페이지
│	│		│	├── UserList.js			# 모든 유저 리스트	
│	│		│	└── UserArticle.js		# 유저의 게시글 리스트
│	│		├──Public
│	│		│	├── CartPageComponent
│	│		│	│	├── CartPage.js		# 장바구니 페이지
│	│		│	│	├── CouponItem.js	# 쿠폰 선택 컴포넌트 (적용 및 미적용 금액 보여주기) 
│	│		│	│	└── EachCartItem.js	# 장바구니에 담긴 아이템 조작(수량, 체크박스) 컴포넌트
│	│		│	├── OrderComponent
│	│		│	│	├── OrderList.js	# 주문한 목록의 항목 리스트
│	│		│	│	└── OrderPage.js	# 주문한 각 아이템 정보 컴포넌트
│	│		│	├── MyPage.js			# 개인정보 전체 컴포넌트
│	│		│	├── MyPost.js			# 개인이 등록한 게시글을 보는 컴포넌트
│	│		│	└── UserInfo.js			# 개인 정보 컴포넌트
│	│		├── LoginPage			# 로그인 페이지
│	│		└── SignupPage			# 회원가입 페이지
│	└── FooterPage
│		└── Footer.js				# Footer
└── Features
	├── GenreMap.js		# 장르별 ID값을 장르항목(항목)으로 변환
	├── JwtDecode.js		# 수신받은 JWT 토큰을 디코드하는 함수
	└── ETC...
```



## 🤜🤛 회고

```markdown
초반, react의 컴포넌트 구조와 state관리를 이해하는데 시간을 많이 들였지만, 궁금증을 가지고 배우려고 노력하니 점점 알아가는게 많아졌다. 
하지만 그만큼 지금 프로젝트에서의 부족한 점이 보이기 시작하였고, 많은 부분이 아쉬움으로 남았다.

💥 아쉬운 점
- React Redux를 이용한 중앙 집중식의 상태관리 (상태관리를 하는데 있어서 많은 코드가 낭비된다고 느껴졌음)
- 컴포넌트의 구조를 체계화하여 재활용 코드 구현 및 리팩토링
- Material UI를 이용하여 더욱 매력적인 페이지 구축
```

