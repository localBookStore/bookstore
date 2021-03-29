package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.service.OrdersService;
import com.webservice.bookstore.web.dto.MemberDto;
import com.webservice.bookstore.web.dto.OrderItemDto;
import com.webservice.bookstore.web.dto.OrdersDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService orderService;

    /*
    주문 생성
    */
    @PostMapping(value = "/order")
    public ResponseEntity createOrder(@RequestBody List<OrderItemDto> orderItemDtoList,
                                      @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        MemberDto memberDto = null;
        try {
            memberDto = MemberDto.builder()
                                .id(customUserDetails.getMember().getId())
                                .build();
        } catch (NullPointerException e) {
            // CustomUserDetails 객체가 null인 경우는 jwt 토큰으로 인증을 거치지 않았다는 의미
            throw new AuthenticationException("인증 오류가 발생했습니다.", e.getCause()) {};
        }

        OrdersDto ordersDto = orderService.addOrder(memberDto, orderItemDtoList);

        return new ResponseEntity(ordersDto, HttpStatus.OK);
    }

    /*
    각 회원 주문 리스트 (구매 내역) 조회
    */
    @GetMapping("/admin/members/{member_id}/orders")
    public ResponseEntity getOrderList(@PathVariable(value = "member_id") Long member_id) {

        MemberDto memberDto = MemberDto.builder().id(member_id).build();

        List<OrdersDto> orderDtoList = orderService.findOrders(memberDto);

        return new ResponseEntity(orderDtoList, HttpStatus.OK);
    }
}
