package com.webservice.bookstore.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webservice.bookstore.domain.entity.item.Item;
import lombok.*;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GetItemDto {
    private Long id;

    private Long category_id;

    private String category_name;

    private String name;

    private String description;

    private String publisher;

    private String author;

    private Integer price;

    private Integer quantity;

    private String isbn;

    private String imageUrl;

    @JsonIgnore
    private int viewCount;

    //Category_name 필드 추가

    public static GetItemDto toDto(Item item) {
        return GetItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .isbn(item.getIsbn())
                .publisher(item.getPublisher())
                .author(item.getAuthor())
                .imageUrl(item.getImageUrl())
                .category_id(item.getCategory().getId())
                .category_name(item.getCategory().getName())
                .build();
    }
}
