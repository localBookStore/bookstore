package com.webservice.bookstore.domain.entity.coupon;

import com.webservice.bookstore.web.controller.CouponController;
import com.webservice.bookstore.web.dto.CouponDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class CouponResource extends EntityModel<CouponDto> {

    public CouponResource(CouponDto couponDto, Link... links) {
        super(couponDto, links);
        add(linkTo(CouponController.class).slash(couponDto.getId()).withSelfRel());
    }
}
