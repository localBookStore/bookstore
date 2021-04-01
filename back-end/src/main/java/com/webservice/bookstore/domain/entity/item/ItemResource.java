package com.webservice.bookstore.domain.entity.item;

import com.webservice.bookstore.web.controller.ItemController;
import com.webservice.bookstore.web.dto.ItemDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ItemResource extends EntityModel<ItemDto> {

    public ItemResource(ItemDto itemDto, Link... links) {
        super(itemDto, links);
        add(linkTo(ItemController.class).slash(itemDto.getId()).withSelfRel());
    }

}
