package com.webservice.bookstore.web.resource;

import com.webservice.bookstore.web.controller.ItemController;
import com.webservice.bookstore.web.dto.ItemDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class DefaultItemResource extends EntityModel<ItemDto.Default> {

    public DefaultItemResource(ItemDto.Default item, Link... links) {
        super(item, links);
        add(linkTo(ItemController.class).slash(item.getId()).withSelfRel());
    }

}
