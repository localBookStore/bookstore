package com.webservice.bookstore.web.dto;

import lombok.*;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@ToString
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
