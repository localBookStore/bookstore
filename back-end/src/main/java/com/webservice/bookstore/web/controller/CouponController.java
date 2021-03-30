package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.domain.entity.coupon.Coupon;
import com.webservice.bookstore.domain.entity.coupon.CouponResource;
import com.webservice.bookstore.service.CouponService;
import com.webservice.bookstore.web.dto.CouponDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping(value = "/api/coupon", produces = MediaTypes.HAL_JSON_VALUE +";charset=utf-8")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponServcie;



    /*
    *   쿠폰 적용 버튼 눌러스시 해당 카테고리의 쿠폰들 조
    * */
    @GetMapping("/{id}")
    public ResponseEntity retrieveCoupons(@PathVariable Long id) {
        List<Coupon> coupons = couponServcie.findCoupons(id);
        List<CouponDto> couponDtos = coupons.stream().map(coupon -> CouponDto.toDto(coupon)).collect(Collectors.toList());
        List<CouponResource> linkList = couponDtos.stream().map(couponDto -> new CouponResource(couponDto)).collect(Collectors.toList());
        CollectionModel<CouponResource> collectionModel = CollectionModel.of(linkList);
        return ResponseEntity.ok(collectionModel);
    }

    /*
        쿠폰 적용
     */
    @PostMapping("/{coupon_id}/orderitems/{orderitem_id}")
    public ResponseEntity applyCoupons(@PathVariable("coupon_id") Long couponId, @PathVariable("orderitem_id") Long orderItemId) {
        Coupon coupon = couponServcie.findById(couponId).orElseThrow(() -> new NullPointerException("해당 쿠폰은 존재하지 않습니다."));
        

    }



}
