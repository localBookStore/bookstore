# Front Part - React

```markdown
#### React app 사용

# react 앱 생성 (최초에만)
npx install create-react-app {app 이름}

# react dependency & library 설치
npm install
( or npm install -g)  # global

# react server 실행 (3000 포트)
npm start
```

### Library

```bash
'react-router-dom' # Apply Domain Routing
'axios' # Asynchronous Request
'react-bootstrap' # Apply Carousel & modal
'react-slick' # For a apply Multi Carousel (Like Netflix...)
'slick-carousel' # react-slcik CSS
'fontawesome 3 Pakage' # for react UI font
    '@fortawesome/fontawesome-svg-core'
    '@fortawesome/free-solid-svg-icons'
    '@fortawesome/react-fontawesome'
'styled-components' # apply for CSS in js
'poished'   # interactive utill funcion
'qs'    # using query string (사용x)
'react-cookie'  # Using Cookie in Browser
'react-hook-form'   # for Using user input form
'jwt-decode'    # Do jwt decode library (using find user info)
'react-google-login'    # for social login of google
'react-paginate'    # apply paginate of items
'react-rating-starts-component' # apply rating star for react
'react-icons'   # for react web icon
'react-material-ui'	# using for various Form
```

### File Structure (In Progress...)

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
│ 	├── books.jpg
│ 	└── open-book.jpg
├── index.css
├── index.js
├── components
│ ├── HeaderPage
|		├── CategoryBar.js		# 장르 / 베스트 / 신간 / 커뮤니티 별로 Link되는 컴포넌트
|		├── CategoryHoverDetail.js		# 장르 버튼에 Mouse Over시 display되는 컴포넌트
|		├── Header.js		# Header Container
|		└── SearchBar.js		# 제목별 검색기능
│ ├── MainPage
│		├── BookListPage
│		│		├── BestBookList.js		# 베스트 아이템 리스트
│		│		├── NewBookList.js		# 신간 아이템 리스트
│		│		└── SearchBookList.js		# 검색 결과 아이템 리스트
│		├── CommunityPage
│		│		├── Community.js		# 전체 커뮤니티 컴포넌트
│		│		├── CommunityDetail.js		# 상세 커뮤니티 게시글 + 댓글 
│		│		├──	ArticleDetail.js		# 상세 커뮤니티 게시글
│		│		├──	CommentDetail.js		# 상세 커뮤니티 댓글
│		│		├──	EachComment.js		# 각 댓글 컴포넌트
│		│		├── CommunityRegister.js		# 게시글 등록
│		│		└── CommunityUpdate.js		# 게시글 수정(업데이트)
│		├── DetailPage
│		│		├── ItemDetail.js		# 상세 도서 컴포넌트
│		│		├── TopDetail.js		# 책의 이미지 / 제목 / 저자 / 출판사 / 가격 / 재고 + 장바구니 / 바로구매
│		│		├── MidDetial.js		# 책의 설명
│		│		├── ReviewList.js		# 책을 구매한 유저의 댓글 및 평점 리스트
│		│		└── Reivew.js		# 각 댓글 컴포넌트
│		├── HomePage
│		│		├── Home.js		# 메인 화면
│		│		├── SlideItem.js		# 하나의 도서에 대한 슬라이드
│		│		├── PickItem.js		# 랜덤 4개의 도서에 대한 멀티 슬라이드
│		│		├── MonthBooks.js		# 랜덤 4개의 도서에 대한 멀티 슬라이드
│		│		└── CustomArrow
│		│				├── NextArrow.css		# 다음 슬라이드 제어 버튼 CSS		
│		│				├── NextArrow.js		# 다음 슬라이드 제어 버튼
│		│				├── PrevArrow.css		# 이전 슬라이드 제어 버튼 CSS
│		│				└── prevArrow.js		# 이전 슬라이드 제어 버튼
│ 	├── UserPage
│		│		├── Admin
│		│		│		├── AllOrderListComponent
│		│		│		│		├── AllOrderList.js		# 모든 유저 목록
│		│		│		│		├── EachUserOrder.js		# 각 유저의 주문 내용
│		│		│		│		└── UserOrder.js		# 각 유저의 상세 주문 내용
│		│		│		├── AdminPage.js		# 관리자 컴포넌트
│		│		│		├── AllItemList.js
│		│		│		├── EachItemList.js
│		│		│		├── PostCoupon.js
│		│		│		├── UserArticle.js
│		│		│		└── UserList.js
│		│		├──	Public
│		│		│		├── CartPageComponent
│		│		│		│		├── CartPage.js
│		│		│		│		├── CouponItem.js
│		│		│		│		└── EachCartItem.js
│		│		│		├── OrderComponent
│		│		│		│		├── OrderPage.js
│		│		│		│		└── OrderList.js
│		│		│		├── MyPage.js
│		│		│		├── MyPost.js
│		│		│		└── UserInfo.js
│		│		├── LoginPage
│		│		└── SignupPage
│ 	└── FooterPage
│				└── Footer.js
└── router
```
