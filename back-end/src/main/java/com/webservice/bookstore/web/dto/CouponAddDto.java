package com.webservice.bookstore.web.dto;

import com.webservice.bookstore.domain.entity.category.Category;
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
public class CouponAddDto {

    private String name;
    private String description;
    private int discountRate;
    private LocalDate endDate;

    public Coupon toEntity() {


        return Coupon.builder()
                .name(this.name)
                .description(this.description)
                .discountRate(this.discountRate)
                .endDate(this.endDate)
                .build();

    }
}
