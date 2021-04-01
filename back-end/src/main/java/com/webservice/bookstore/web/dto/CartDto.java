package com.webservice.bookstore.web.dto;

import com.webservice.bookstore.domain.entity.cart.Cart;
import com.webservice.bookstore.domain.entity.item.ItemResource;
import com.webservice.bookstore.domain.entity.member.Member;
import lombok.*;

public class CartDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Default {

        private Long id;
        private Long member_id;
        private ItemDto itemDto;
        private Integer price;
        private Integer orderCount;

        // Entity -> DTO
        public static Default of(Cart cart) {
            return Default.builder()
                          .id(cart.getId())
                          .member_id(cart.getMember().getId())
                          .itemDto(ItemDto.of(cart.getItem()))
                          .price(cart.getPrice())
                          .orderCount(cart.getOrderCount())
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
        public CartDto.Response toResponse() {
            return CartDto.Response.builder()
                    .id(this.getId())
                    .member_id(this.getMember_id())
                    .orderItem(new ItemResource(this.getItemDto()))
                    .price(this.getPrice())
                    .orderCount(this.getOrderCount())
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
        private ItemResource orderItem;
        private Integer price;
        private Integer orderCount;
    }


}
