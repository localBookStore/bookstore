package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.category.Category;
import com.webservice.bookstore.domain.entity.coupon.Coupon;
import com.webservice.bookstore.domain.entity.coupon.CouponRepository;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.web.dto.CouponAddDto;
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

    @Transactional
    public void issueCoupon(CouponAddDto couponDto) {
        List<Member> members = this.memberRepository.findAll();
        Category category = Category.builder().id(couponDto.getCategory_id()).name(couponDto.getCategory_name()).build();

        members.stream().forEach(member -> {
            Coupon savedCoupon = savedNewCoupon(couponDto, category);
            member.addCoupon(savedCoupon);
        });
    }

    private Coupon savedNewCoupon(CouponAddDto couponDto, Category category) {
        Coupon coupon = Coupon.builder()
                .name(couponDto.getName())
                .endDate(couponDto.getEndDate())
                .discountRate(couponDto.getDiscountRate())
                .category(category)
                .description(couponDto.getDescription())
                .build();

        return couponRepository.save(coupon);
    }


}
