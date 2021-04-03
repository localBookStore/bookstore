package com.webservice.bookstore.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webservice.bookstore.domain.entity.cart.Cart;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.web.resource.DefaultItemResource;
import lombok.*;

import java.time.LocalDateTime;

public class CartDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Default {

        private Long id;
        private Long member_id;
        private ItemDto.Default itemDto;
        private Integer price;
        private Integer orderCount;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createDate;

        // Entity -> DTO
        public static Default of(Cart cart) {
            return Default.builder()
                    .id(cart.getId())
                    .member_id(cart.getMember().getId())
                    .itemDto(ItemDto.Default.of(cart.getItem()))
                    .price(cart.getPrice())
                    .orderCount(cart.getOrderCount())
                    .createDate(cart.getCreatedDate())
                    .build();
        }

        // DTO -> Entity
        public Cart toEntity() {

            Member member = Member.builder().id(this.member_id).build();

            return Cart.builder()
                    .id(this.id)
                    .member(member)
                    .item(this.itemDto.toEntity())
                    .price(this.price)
                    .orderCount(this.orderCount)
                    .build();
        }

        // Default -> Response
        public Response toResponse() {
            return Response.builder()
                    .id(this.id)
                    .member_id(this.member_id)
                    .item(new DefaultItemResource(this.itemDto))
                    .price(this.price)
                    .orderCount(this.orderCount)
                    .createDate(this.createDate)
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long id;
        private Long member_id;
        private DefaultItemResource item;
        private Integer price;
        private Integer orderCount;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createDate;
    }


}