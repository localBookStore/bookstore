package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.coupon.Coupon;
import com.webservice.bookstore.domain.entity.coupon.CouponRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public List<Coupon> findCoupons(Long id) {
        List<Coupon> coupons = couponRepository.findCouponList(id);
        return coupons;
    }

    public Optional<Coupon> findById(Long id) {
        Optional<Coupon> coupon = couponRepository.findById(id);
        return coupon;
    }

    public List<Coupon> giveCoupons() {
        List<Coupon> coupons = this.couponRepository.giveCoupons();
        return coupons;
    }
}
