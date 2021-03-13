package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.service.OrdersService;
import com.webservice.bookstore.web.dto.MemberDto;
import com.webservice.bookstore.web.dto.OrderItemDto;
import com.webservice.bookstore.web.dto.OrdersDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/order")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000/"})
public class OrdersController {

    private final OrdersService orderService;

    /*
    주문 생성
    */
    @PostMapping(value = "/")
    public ResponseEntity createOrder(@RequestBody List<OrderItemDto> orderItemDtoList) {
//                                      @AuthenticationPrincipal CustomUserDetails customUserDetails) {

//        MemberDto memberDto = MemberDto.builder().id(customUserDetails.getMember().getId()).build();

        MemberDto memberDto = MemberDto.builder().id(1L).build();

        OrdersDto ordersDto = orderService.addOrder(memberDto, orderItemDtoList);

        return new ResponseEntity(ordersDto, HttpStatus.OK);
    }

}
