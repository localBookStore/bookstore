package com.webservice.bookstore.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webservice.bookstore.domain.entity.category.Category;
import com.webservice.bookstore.domain.entity.category.CategoryRepository;
import com.webservice.bookstore.domain.entity.coupon.Coupon;
import com.webservice.bookstore.domain.entity.coupon.CouponRepository;
import com.webservice.bookstore.domain.entity.item.ItemRepository;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.domain.entity.member.MemberRole;
import com.webservice.bookstore.web.dto.CouponAddDto;
import com.webservice.bookstore.web.dto.CouponDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
class CouponControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CouponRepository couponRepository;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
        categoryRepository.deleteAll();
        memberRepository.deleteAll();
        couponRepository.deleteAll();
    }


    @Transactional
    @Test
    public void 쿠폰_발급_테스트() throws Exception {
        //given

        Category category = Category.builder()
                .id(10L)
                .name("총류")
                .build();
        categoryRepository.save(category);

        IntStream.rangeClosed(1,3).forEach(i -> {
            Member member = Member.builder()
                    .email("ddd" + i)
                    .password("1234")
                    .nickName("회원 " + i)
                    .role(MemberRole.USER)
                    .build();
            memberRepository.save(member);
        });

        CouponAddDto couponAddDto = CouponAddDto.builder()
                .category_id(category.getId())
                .category_name(category.getName())
                .description("신상 쿠폰 입니다.")
                .endDate(null)
                .name("신상스")
                .discountRate(30)
                .build();

        //when

        //then
        this.mockMvc.perform(post("/api/coupon")
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsString(couponAddDto))
        )
                .andDo(print())
                .andExpect(status().isOk())
                ;

        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            System.out.println("member.coupon = " + member.getCoupons().get(0).getMember().getId());
        }

        System.out.println("====================================");
        List<Coupon> coupons = couponRepository.findAll();
        for (Coupon coupon : coupons) {
            System.out.println("coupon : " + coupon.getMember());
        }
    }


}