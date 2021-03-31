package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.exception.UnauthorizedException;
import com.webservice.bookstore.service.OrdersService;
import com.webservice.bookstore.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService orderService;

    private void verifyAuthentication(CustomUserDetails customUserDetails) {
        if(customUserDetails == null || customUserDetails.equals("")) {
            throw new UnauthorizedException("인증 오류가 발생했습니다.");
        }
    }

    /*
    주문 생성
    */
//    @PostMapping(value = "/order")
//    public ResponseEntity createOrder(@RequestBody List<OrderItemDto> orderItemDtoList,
    @PostMapping(value = "/order")
    public ResponseEntity createOrder(@RequestBody Map<String, Object> map,
                                      @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        verifyAuthentication(customUserDetails);

        MemberDto.Default memberDto = MemberDto.Default.builder()
                                               .id(customUserDetails.getMember().getId())
                                               .address(String.valueOf(map.get("address")))
                                               .build();
        CouponDto couponDto = CouponDto.builder()
                .id(Long.parseLong(String.valueOf(map.get("coupon_id"))))
                .build();

        List<Map<String, Object>> orderList = (List<Map<String, Object>>) map.get("orderList");

        List<CartDto> cartDtoList           = new ArrayList<>();
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        for(Map<String, Object> objectMap : orderList) {
            cartDtoList.add(CartDto.builder().id(Long.parseLong(String.valueOf(objectMap.get("cart_id")))).build());
            ItemDto itemDto = ItemDto.builder().id(Long.parseLong(String.valueOf(objectMap.get("item_id")))).build();
            orderItemDtoList.add(OrderItemDto.builder()
                                            .itemDto(itemDto)
                                            .orderCount(Integer.parseInt(String.valueOf(objectMap.get("orderCount"))))
                                            .build()
                                );
        }

        orderService.addOrder(memberDto, cartDtoList, couponDto, orderItemDtoList);

        return new ResponseEntity("success", HttpStatus.OK);

    }

    /*
    관리자 페이지 각 회원 주문 리스트 (구매 내역) 조회
    */
    @GetMapping("/admin/members/{member_id}/orders")
    public ResponseEntity getOrderList(@PathVariable(value = "member_id") Long member_id) {

        MemberDto.Default memberDto = MemberDto.Default.builder().id(member_id).build();

        List<OrdersDto> orderDtoList = orderService.findOrders(memberDto);

        return new ResponseEntity(orderDtoList, HttpStatus.OK);
    }

    /*
    관리자 페이지 주문 취소
    */
    @PatchMapping("/admin/orders/cancel/{order_id}")
    public ResponseEntity cancelOrder(@RequestBody @PathVariable(value = "order_id") Long orders_id,
                                      @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        MemberDto.Default memberDto = MemberDto.Default
                                               .builder().id(customUserDetails.getMember().getId()).build();
        OrdersDto ordersDto = OrdersDto.builder().id(orders_id).build();

        orderService.cancelOrder(memberDto, ordersDto);

        return new ResponseEntity("success", HttpStatus.OK);
    }
}
