package com.webservice.bookstore.web.dto;

import com.webservice.bookstore.domain.entity.cart.Cart;
import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.member.Member;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {
    private Long id;

    private Long member_id;

//    private Long item_id;
    private ItemDto itemDto;

    private Integer price;

    private Integer orderCount;

    // Entity -> DTO
    public static CartDto of(Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .member_id(cart.getMember().getId())
//                .item_id(cart.getItem().getId())
                .itemDto(ItemDto.of(cart.getItem()))
                .price(cart.getPrice())
                .orderCount(cart.getOrderCount())
                .build();
    }

    // DTO -> Entity
    public Cart toEntity() {

        Member member = Member.builder().id(this.member_id).build();
//        Item item = Item.builder().id(this.item_id).build();

        return Cart.builder()
                .id(this.id)
                .member(member)
//                .item(item)
                .item(this.itemDto.toEntity())
                .price(this.price)
                .orderCount(this.orderCount)
                .build();
    }
}
