package com.webservice.bookstore.web.dto;

import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.order.Orders;
import com.webservice.bookstore.domain.entity.orderItem.OrderItem;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemDto {

    private Long id;

    private Long orders_id;

    private Long item_id;
//    private ItemDto itemDto;

    private Integer orderCount;

    private Integer orderPrice;

    // Entity -> DTO
    public static OrderItemDto of(OrderItem orderItem) {
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .orders_id(orderItem.getOrder().getId())
                .item_id(orderItem.getItem().getId())
//                .itemDto(ItemDto.of(orderItem.getItem()))
                .orderCount(orderItem.getOrderCount())
                .orderPrice(orderItem.getOrderPrice())
                .build();
    }

    // DTO -> Entity
    public OrderItem toEntity() {

        Orders orders = Orders.builder().id(this.orders_id).build();
        Item item = Item.builder().id(this.item_id).build();
//        Item item = Item.builder().id(this.itemDto.getId()).build();

        return OrderItem.builder()
                .id(this.id)
                .order(orders)
                .item(item)
                .orderCount(this.orderCount)
                .orderPrice(this.orderPrice)
                .build();
    }

}
