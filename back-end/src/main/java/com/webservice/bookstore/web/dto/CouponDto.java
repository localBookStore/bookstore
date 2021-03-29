package com.webservice.bookstore.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webservice.bookstore.domain.entity.coupon.Coupon;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;

    public static CouponDto toDto(Coupon coupon) {
        return CouponDto.builder()
                .name(coupon.getName())
                .description(coupon.getDescription())
                .discountRate(coupon.getDiscountRate())
                .endDate(coupon.getEndDate())
                .build()
                ;
    }


}
