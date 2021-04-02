package com.webservice.bookstore.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webservice.bookstore.domain.entity.category.Category;
import com.webservice.bookstore.domain.entity.item.Item;
import lombok.*;

public class ItemDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Default {
        private Long id;

        private Long category_id;

//    private String category_name;

        private String name;

        private String description;

        private String publisher;

        private String author;

        private Integer price;

        private Integer quantity;

        private String isbn;

        private String imageUrl;


        // Entity -> DTO
        public static Default of(Item item) {
            return Default.builder()
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
//                .category_name(item.getCategory().getName())
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

    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class GetItemDto {
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


    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class ItemAddDto {

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


}
