package com.webservice.bookstore.domain.entity.item;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.webservice.bookstore.web.dto.ItemDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class ItemLinkResource extends EntityModel<ItemDto> {

    public ItemLinkResource(ItemDto itemDto, Link... links) {
        super(itemDto, links);
    }

}
