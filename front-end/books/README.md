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
'qs'    # using query string
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
|		├── CategoryBar.js
|		├── CategoryHoverDetail.js
|		├── Header.js
|		└── SearchBar.js
│ ├── MainPage
│		├── BookListPage
│		│		├── BestBookList.js
│		│		├── NewBookList.js
│		│		└── SearchBookList.js
│		├── CommunityPage
│		│		├── Community.js
│		│		├── CommunityDetail.js
│		│		├──	ArticleDetail.js
│		│		├──	CommentDetail.js
│		│		├──	EachComment.js
│		│		├── CommunityRegister.js
│		│		└── CommunityUpdate.js
│		├── DetailPage
│		│		├── ItemDetail.js
│		│		├── TopDetail.js
│		│		├── MidDetial.js
│		│		├── ReviewList.js
│		│		└── Reivew.js
│		├── HomePage
│		│		├── Home.js
│		│		├── SlideItem.js
│		│		├── PickItem.js
│		│		├── MonthBooks.js
│		│		└── CustomArrow
│		│				├── NextArrow.css		
│		│				├── NextArrow.js
│		│				├── PrevArrow.css
│		│				└── prevArrow.js
│ 	├── UserPage
│		│		├── Admin
│		│		│		├── AllOrderListComponent
│		│		│		│		├── AllOrderList.js
│		│		│		│		├── EachUserOrder.js
│		│		│		│		└── UserOrder.js
│		│		│		├── AdminPage.js
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
