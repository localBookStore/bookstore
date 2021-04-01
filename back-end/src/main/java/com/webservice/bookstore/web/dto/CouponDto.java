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
    private Long category_id;
    private String category_name;
    private Long member_id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;

    public static CouponDto toDto(Coupon coupon) {
        return CouponDto.builder()
                .id(coupon.getId())
                .name(coupon.getName())
                .description(coupon.getDescription())
                .discountRate(coupon.getDiscountRate())
                .endDate(coupon.getEndDate())
                .category_id(coupon.getCategory().getId())
                .category_name(coupon.getCategory().getName())
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
                .category(Category.builder().id(this.category_id).name(this.category_name).build())
                .build();

    }



}
