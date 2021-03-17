package com.webservice.bookstore.domain.entity.cart;

import com.webservice.bookstore.web.dto.CartDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class CartLinkResource extends EntityModel<CartDto> {

    public CartLinkResource(CartDto cartDto, Link... links) {
        super(cartDto, links);
    }
}
