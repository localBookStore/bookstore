package com.webservice.bookstore.web.dto;

import com.webservice.bookstore.domain.entity.category.Category;
import com.webservice.bookstore.domain.entity.item.Item;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class ItemDto {
    private Long id;

    private String name;

    private String description;

    private Integer price;

    private Integer quantity;

    private String isbn;

    private String publisher;

    private String author;

    private String imageUrl;

    private Long category_id;

    // Entity -> DTO
    public static ItemDto of(Item item) {
        return ItemDto.builder()
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
                .build();
    }

    // DTO -> Entity
    public Item toEntity() {

        Category category = Category.builder().id(this.category_id).build();

        return Item.builder()
                    .id(this.id)
                    .name(this.name)
                    .description(this.description)
                    .price(this.price)
                    .quantity(this.quantity)
                    .isbn(this.isbn)
                    .publisher(this.publisher)
                    .author(this.author)
                    .imageUrl(this.imageUrl)
                    .category(category)
                    .build();
    }
}
