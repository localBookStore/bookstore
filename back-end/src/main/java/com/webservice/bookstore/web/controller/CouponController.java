package com.webservice.bookstore.web.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webservice.bookstore.domain.entity.coupon.Coupon;
import com.webservice.bookstore.domain.entity.coupon.CouponResource;
import com.webservice.bookstore.service.CouponService;
import com.webservice.bookstore.web.dto.CouponDto;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping(value = "/api/coupon", produces = MediaTypes.HAL_JSON_VALUE +";charset=utf-8")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponServcie;


    @GetMapping
    public ResponseEntity retrieveCoupons() {
        List<Coupon> coupons = couponServcie.findCoupons();
        List<CouponDto> couponDtos = coupons.stream().map(coupon -> CouponDto.toDto(coupon)).collect(Collectors.toList());
        List<CouponResource> linkList = couponDtos.stream().map(couponDto -> new CouponResource(couponDto)).collect(Collectors.toList());
        CollectionModel<CouponResource> collectionModel = CollectionModel.of(linkList);
        return collectionModel

    }



}
