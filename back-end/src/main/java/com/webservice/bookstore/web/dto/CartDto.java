package com.webservice.bookstore.web.dto;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class CartDto {
    private Long id;

    private Long member_id;

    private Long item_id;

    private Integer price;

    private Integer quantity;
}
