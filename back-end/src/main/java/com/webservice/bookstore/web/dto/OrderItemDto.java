package com.webservice.bookstore.web.dto;

import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.item.ItemResource;
import com.webservice.bookstore.domain.entity.order.Orders;
import com.webservice.bookstore.domain.entity.orderItem.OrderItem;
import lombok.*;

public class OrderItemDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Default {

        private Long id;
        private Long orders_id;
        private ItemDto itemDto;
        private Integer orderCount;
        private Integer orderPrice;

        // Entity -> DTO
        public static Default of(OrderItem orderItem) {
            return Default.builder()
                          .id(orderItem.getId())
                          .orders_id(orderItem.getOrders().getId())
                          .itemDto(ItemDto.of(orderItem.getItem()))
                          .orderCount(orderItem.getOrderCount())
                          .orderPrice(orderItem.getOrderPrice())
                          .build();
        }

        // DTO -> Entity
        public OrderItem toEntity() {

            Orders orders = Orders.builder().id(this.orders_id).build();
            Item item = Item.builder().id(this.itemDto.getId()).build();

            return OrderItem.builder()
                            .id(this.id)
                            .orders(orders)
                            .item(item)
                            .orderCount(this.orderCount)
                            .orderPrice(this.orderPrice)
                            .build();
        }

        // Default -> Response
        public Response toResponse() {
            return Response.builder()
                    .id(this.id)
                    .orders_id(this.orders_id)
                    .orderedItem(new ItemResource(this.itemDto))
                    .orderCount(this.orderCount)
                    .orderPrice(this.orderPrice)
                    .build();
        }

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long id;
        private Long orders_id;
        private ItemResource orderedItem;
        private Integer orderCount;
        private Integer orderPrice;
    }

}
