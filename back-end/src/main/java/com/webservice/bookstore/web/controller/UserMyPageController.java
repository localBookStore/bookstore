package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.exception.UnauthorizedException;
import com.webservice.bookstore.service.OrdersService;
import com.webservice.bookstore.web.dto.MemberDto;
import com.webservice.bookstore.web.dto.OrdersDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/api/mypage", produces = MediaTypes.HAL_JSON_VALUE+";charset=utf-8")
public class UserMyPageController {

    private final OrdersService orderService;

    @GetMapping
    public ResponseEntity searchMyInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        verifyAuthentication(customUserDetails);

        Member member = customUserDetails.getMember();
        MemberDto.MyInfoRequest myInfoRequest = MemberDto.MyInfoRequest.builder()
                .email(member.getEmail())
                .nickName(member.getNickName())
                .address(member.getAddress())
                .provider(String.valueOf(member.getProvider()))
                .build();

        return ResponseEntity.ok(myInfoRequest);
    }

    /*
    마이페이지 주문 내역 조회
    */
    @GetMapping("/order")
    public ResponseEntity getMyOrderList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        verifyAuthentication(customUserDetails);

        Member member = customUserDetails.getMember();
        MemberDto.Default memberDto = MemberDto.Default.builder().id(member.getId()).build();

        List<OrdersDto.Default> orderDtoList = orderService.findOrders(memberDto);

        List<OrdersDto.Response> orderList = new ArrayList<>();
        orderDtoList.stream().forEach(orderDto -> orderList.add(orderDto.toResponse()));

        return ResponseEntity.ok(orderList);
    }

    /*
    일반 유저 페이지 주문 취소 요청
    */
    @PatchMapping("/order/{order_id}")
    public ResponseEntity cancelOrder(@RequestBody @PathVariable(value = "order_id") Long orders_id,
                                      @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        verifyAuthentication(customUserDetails);

        OrdersDto.Default ordersDto = OrdersDto.Default.builder()
                                                       .id(orders_id)
                                                       .member_id(customUserDetails.getMember().getId())
                                                       .build();

        List<OrdersDto.Default> orderDtoList = orderService.cancelOrder(ordersDto);

        List<OrdersDto.Response> orderList = new ArrayList<>();
        orderDtoList.stream().forEach(orderDto -> orderList.add(orderDto.toResponse()));

        return ResponseEntity.ok(orderList);
    }

    private void verifyAuthentication(CustomUserDetails customUserDetails) {
        if(customUserDetails == null) {
            throw new UnauthorizedException("인증 오류가 발생했습니다.");
        } else if(!customUserDetails.isEnabled()) {
            throw new UnauthorizedException("계정이 잠겨있습니다. 관리자에게 문의해주시길 바랍니다.");
        }
    }
}
