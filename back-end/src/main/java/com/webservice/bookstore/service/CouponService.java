package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.coupon.Coupon;
import com.webservice.bookstore.domain.entity.coupon.CouponRepository;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.web.dto.CouponDto;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    public List<Coupon> findCoupons(Long memberId) {
        List<Coupon> coupons = couponRepository.findCouponList(memberId);
        return coupons;
    }

    public Optional<Coupon> findById(Long id) {
        Optional<Coupon> coupon = couponRepository.findById(id);
        return coupon;
    }

//    @Transactional
//    public void giveCoupon(CouponDto couponDto) {
//        List<Long> memberIds = memberRepository.findAllMemberId();
//        for (Long memberId : memberIds) {
//            this.couponRepository.addCoupon(couponDto.getDescription(),couponDto.getDiscountRate(),couponDto.getName(), couponDto.getCategory_id(), memberId);
//        }
//    }


}
