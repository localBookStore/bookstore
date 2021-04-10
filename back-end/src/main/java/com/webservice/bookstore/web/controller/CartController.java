package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.exception.UnauthorizedException;
import com.webservice.bookstore.service.CartService;
import com.webservice.bookstore.web.dto.CartDto;
import com.webservice.bookstore.web.dto.ItemDto;
import com.webservice.bookstore.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping(value = "/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private void verifyAuthentication(CustomUserDetails customUserDetails) {
        if(customUserDetails == null) {
            throw new UnauthorizedException("인증 오류가 발생했습니다.");
        } else if(!customUserDetails.isEnabled()) {
            throw new UnauthorizedException("계정이 잠겨있습니다. 관리자에게 문의해주시길 바랍니다.");
        }
    }

    /*
    장바구니 목록 조회 요청 핸들러
    */
    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getCartItemList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        // 세션에 저장된 로그인 계정 정보를 통해 장바구니 목록 조회 예정
        List<CartDto.Default> cartDtoList = null;

        verifyAuthentication(customUserDetails);
        cartDtoList = cartService.findByMemberId(customUserDetails.getMember().getId());

        List<CartDto.Response> cartList = cartDtoList.stream()
                .map(cartDto -> cartDto.toResponse())
                .collect(Collectors.toList());
        return ResponseEntity.ok(cartList);
    }

    /*
    장바구니 아이템 추가 요청 핸들러
    */
    @PostMapping(value = "/{item_id}")
    public ResponseEntity addCartItem(@PathVariable("item_id") Long item_id,
                                      @RequestBody CartDto.Default cartDto,
                                      @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        verifyAuthentication(customUserDetails);
        cartDto.setMember_id(customUserDetails.getMember().getId());
        cartDto.setItemDto(ItemDto.Default.builder().id(item_id).build());

        try {
            cartService.addCartEntity(cartDto);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity("해당 아이템이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity("해당 아이템이 이미 장바구니에 담겨있습니다. 장바구니를 확인해주세요", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("success", HttpStatus.OK);
    }

    /*
    장바구니 아이템 수량 업데이트 요청 핸들러
    */
    @PatchMapping(value = "/{cart_id}")
    public ResponseEntity updateCartItem(@PathVariable("cart_id") Long cart_id,
                                         @RequestBody CartDto.Default cartDto,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        verifyAuthentication(customUserDetails);
        cartService.updateQuantity(cart_id, cartDto.getOrderCount());

        return new ResponseEntity("success", HttpStatus.OK);
    }

    /*
    장바구니 아이템 삭제 요청 핸들러
    */
    @DeleteMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity deleteCartItem(@RequestBody List<Long> cartIdList,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        List<CartDto.Default> cartDtoList = new ArrayList<>();
        for(int i = 0 ; i < cartIdList.size() ; i++) {
            cartDtoList.add(CartDto.Default.builder().id(cartIdList.get(i)).build());
        }

        verifyAuthentication(customUserDetails);

        MemberDto.Default memberDto = MemberDto.Default
                .builder().id(customUserDetails.getMember().getId()).build();
        cartDtoList = cartService.deleteCartItem(memberDto, cartDtoList);

        List<CartDto.Response> cartList = cartDtoList.stream()
                .map(cartDto -> cartDto.toResponse())
                .collect(Collectors.toList());

        return new ResponseEntity(cartList, HttpStatus.OK);
    }
}