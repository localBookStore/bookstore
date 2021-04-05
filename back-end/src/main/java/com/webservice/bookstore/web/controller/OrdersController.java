package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.domain.entity.cart.Cart;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.exception.UnauthorizedException;
import com.webservice.bookstore.service.OrdersService;
import com.webservice.bookstore.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService orderService;

    private void verifyAuthentication(CustomUserDetails customUserDetails) {
        if(customUserDetails == null) {
            throw new UnauthorizedException("인증 오류가 발생했습니다.");
        } else if(!customUserDetails.isEnabled()) {
            throw new UnauthorizedException("계정이 잠겨있습니다. 관리자에게 문의해주시길 바랍니다.");
        }
    }

    /*
    주문 생성
    */
    @PostMapping(value = "/order")
    public ResponseEntity createOrder(@RequestBody Map<String, Object> map,
                                      @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                      Errors errors) {

        verifyAuthentication(customUserDetails);

        MemberDto.Default memberDto = MemberDto.Default.builder()
                .id(customUserDetails.getMember().getId())
                .address(String.valueOf(map.get("address")))
                .build();
        CouponDto couponDto = null;
        if(!ObjectUtils.isEmpty(map.get("coupon_id"))){
            couponDto = CouponDto.builder()
                    .id(Long.parseLong(String.valueOf(map.get("coupon_id"))))
                    .build();
        }
        List<Map<String, Object>> orderList = (List<Map<String, Object>>) map.get("orderList");

        List<CartDto.Default> cartDtoList           = new ArrayList<>();
        List<OrderItemDto.Default> orderItemDtoList = new ArrayList<>();
        for(Map<String, Object> objectMap : orderList) {
            cartDtoList.add(CartDto.Default.builder().id(Long.parseLong(String.valueOf(objectMap.get("cart_id")))).build());
            ItemDto.Default itemDto = ItemDto.Default.builder().id(Long.parseLong(String.valueOf(objectMap.get("item_id")))).build();
            orderItemDtoList.add(OrderItemDto.Default.builder()
                    .itemDto(itemDto)
                    .orderCount(Integer.parseInt(String.valueOf(objectMap.get("orderCount"))))
                    .build()
            );
        }

        orderService.addOrder(memberDto, couponDto, cartDtoList, orderItemDtoList, errors);

        return new ResponseEntity("success", HttpStatus.OK);

    }

}