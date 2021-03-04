package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.category.Category;
import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.web.dto.ItemDto;

import java.util.List;

public interface ItemService {
    default ItemDto entityToDto(Item item){
        ItemDto dto = ItemDto.builder()
                .id(item.getId())
                .category_id(item.getCategory().getId())
                .description(item.getDescription())
                .imageUrl(item.getImageUrl())
                .author(item.getAuthor())
                .publisher(item.getPublisher())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .isbn(item.getIsbn())
                .name(item.getName())
                .build();
        return dto;
    }

    default Item dtoToEntity(ItemDto dto){
        Item item =Item.builder().category(Category.builder().id(dto.getCategory_id()).build())
                .isbn(dto.getIsbn())
                .description(dto.getDescription())
                .author(dto.getAuthor())
                .publisher(dto.getPublisher())
                .price(dto.getPrice())
                .name(dto.getName())
                .quantity(dto.getQuantity())
                .id(dto.getId())
                .imageUrl(dto.getImageUrl())
                .build();
        return item;
    }

    List<Item> getRandomList(int cnt);
}
