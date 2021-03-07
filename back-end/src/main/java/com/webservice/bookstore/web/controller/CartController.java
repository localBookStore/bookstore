package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.service.CartService;
import com.webservice.bookstore.web.dto.CartDto;
import com.webservice.bookstore.web.dto.ItemDto;
import com.webservice.bookstore.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000/"})
public class CartController {

    private final CartService cartService;

    /*
    장바구니 목록 조회 요청 핸들러
    */
    @GetMapping(value = "/cart/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<CartDto>> getCartItemList() {

        // 세션에 저장된 로그인 계정 정보를 통해 장바구니 목록 조회 예정
        List<CartDto> cartList = cartService.findByMemberId((long) 1);

        return new ResponseEntity<>(cartList, HttpStatus.OK);
    }

    /*
    장바구니 아이템 추가 요청 핸들러
    */
    @PostMapping(value = "/cart/{item_id}")
    public ResponseEntity<CartDto> addCartItem(@PathVariable("item_id") Long item_id,
                                               @RequestBody CartDto cartDto) {
        cartDto.setMember_id((long) 12);
        cartDto.setItem_id(item_id);

        CartDto resCartDto = cartService.addCartEntity(cartDto);

        return new ResponseEntity(resCartDto, HttpStatus.OK);
    }
}
