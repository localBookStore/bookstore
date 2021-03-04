package com.webservice.bookstore.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ItemDto {

    private Long id;

    private Long category_id;

    private String name;

    private String description;

    private String author;

    private String publisher;

    private Integer price;

    private Integer quantity;

    private String isbn;

    private String imageUrl;


}
