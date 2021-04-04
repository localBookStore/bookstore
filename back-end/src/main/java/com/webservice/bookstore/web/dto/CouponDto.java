package com.webservice.bookstore.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webservice.bookstore.domain.entity.category.Category;
import com.webservice.bookstore.domain.entity.coupon.Coupon;
import com.webservice.bookstore.domain.entity.member.AuthProvider;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponDto {

    private Long id;
    private String name;
    private String description;
    private int discountRate;
    private Long member_id;
//    private Long category_id;
//    private String category_name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;

    public static CouponDto of(Coupon coupon) {
        return CouponDto.builder()
                .id(coupon.getId())
                .name(coupon.getName())
                .description(coupon.getDescription())
                .discountRate(coupon.getDiscountRate())
                .endDate(coupon.getEndDate())
                .member_id(coupon.getMember().getId())
                .build()
                ;
    }

    public Coupon toEntity() {
        return Coupon.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .member(Member.builder().id(this.member_id).build())
                .endDate(this.endDate)
                .discountRate(this.discountRate)
                .build();

    }



}
