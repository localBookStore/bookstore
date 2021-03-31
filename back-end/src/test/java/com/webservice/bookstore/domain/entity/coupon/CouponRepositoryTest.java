package com.webservice.bookstore.domain.entity.coupon;

import com.webservice.bookstore.domain.entity.category.Category;
import com.webservice.bookstore.domain.entity.category.CategoryRepository;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class CouponRepositoryTest {

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @BeforeEach
    void setUp() {
        couponRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void 카테고리별_쿠폰조회() throws Exception {
        //given
        Category category = Category.builder()
                .id(1L)
                .name("역사")
                .build();

        Category category2 = Category.builder()
                .id(2L)
                .name("과학")
                .build();
        categoryRepository.save(category);
        categoryRepository.save(category2);

        Coupon coupon = Coupon.builder()
                .category(category)
                .name("역사 쿠폰")
                .description("좋은 쿠폰")
                .discountRate(30)
                .build();
        couponRepository.save(coupon);

        //when

        //then
        List<Coupon> couponList =
                this.couponRepository.findCouponList(category.getId());
        for (Coupon coupon1 : couponList) {
            System.out.println("Coupon : " + coupon1);
        }
        assertThat(couponList.get(0).getCategory()).isEqualTo(category);
    }


//    @Test
//    public void test2() throws Exception {
//        //given
//        Member member = Member.builder()
//                .email("ddd@email.com")
//                .password("1234")
//                .build();
//        this.memberRepository.save(member);
//
//        Category category = Category.builder()
//                .id(1L)
//                .name("역사")
//                .build();
//
//        Category category2 = Category.builder()
//                .id(2L)
//                .name("과학")
//                .build();
//        categoryRepository.save(category);
//        categoryRepository.save(category2);
//
//        Coupon coupon = Coupon.builder()
//                .category(category)
//                .name("역사 쿠폰")
//                .description("좋은 쿠폰")
//                .member(member)
//                .discountRate(30)
//                .build();
//        couponRepository.save(coupon);
//
//        //when
//
//        //then
//        memberService.addCoupon(member.getId(), coupon);
//        List<Coupon> couponList = couponRepository.findCouponList(category.getId());
//        for (Coupon newCoupon : couponList) {
//            System.out.println("Coupon: " + newCoupon);
//        }
//    }


}