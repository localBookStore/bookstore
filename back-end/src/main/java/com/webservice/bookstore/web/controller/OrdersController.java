package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.exception.UnauthorizedException;
import com.webservice.bookstore.exception.ValidationException;
import com.webservice.bookstore.service.OrdersService;
import com.webservice.bookstore.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity createOrder(@RequestBody @Valid OrdersDto.OrderRequest orderRequest, BindingResult bindingResult,
                                      @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        verifyAuthentication(customUserDetails);

        if(bindingResult.hasErrors()) {
            throw new ValidationException("주문 유효성 실패", bindingResult.getFieldErrors());
        }

        MemberDto.Default memberDto = MemberDto.Default.builder()
                                               .id(customUserDetails.getMember().getId())
                                               .address(orderRequest.getAddress())
                                               .build();

        List<CartDto.Default> orderList = orderRequest.getOrderList();

        CouponDto couponDto = null;
        if(orderRequest.getCoupon_id() != null) {
            couponDto = CouponDto.builder()
                                 .id(orderRequest.getCoupon_id())
                                 .build();
        }


        orderService.addOrder(memberDto, couponDto, orderList, bindingResult);

        return new ResponseEntity("success", HttpStatus.OK);

    }

}