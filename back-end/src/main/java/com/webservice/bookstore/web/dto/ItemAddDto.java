package com.webservice.bookstore.web.dto;

import com.webservice.bookstore.domain.entity.category.Category;
import com.webservice.bookstore.domain.entity.item.Item;
import lombok.*;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ItemAddDto {

    private String name;

    private Long category_id;

    private String description;

    private String publisher;

    private String author;

    private Integer price;

    private Integer quantity;

    private String isbn;

    private String imageUrl;


    public Item toEntity() {

        Category category = Category.builder().id(this.category_id).build();
        return Item.builder()
                .name(this.name)
                .category(category)
                .description(this.description)
                .publisher(this.publisher)
                .price(this.price)
                .quantity(this.quantity)
                .isbn(this.isbn)
                .imageUrl(this.imageUrl)
                .build();
    }

}
