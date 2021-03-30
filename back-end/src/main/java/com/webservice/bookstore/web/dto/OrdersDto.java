package com.webservice.bookstore.web.dto;

import com.webservice.bookstore.domain.entity.delivery.Delivery;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.order.Orders;
import com.webservice.bookstore.domain.entity.order.OrdersEnum;
import com.webservice.bookstore.domain.entity.orderItem.OrderItem;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrdersDto {

    private Long id;

    private Long member_id;

    private Long delivery_id;

    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    private Integer paymentAmount;

    private Integer deliveryCharge;

    private OrdersEnum status;

    // Entity -> DTO
    public static OrdersDto of(Orders orders) {

        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        orders.getOrderItems().stream().forEach(orderItem -> orderItemDtoList.add(OrderItemDto.of(orderItem)));

        return OrdersDto.builder()
                .id(orders.getId())
                .member_id(orders.getMember().getId())
                .delivery_id(orders.getDelivery().getId())
                .orderItemDtoList(orderItemDtoList)
                .paymentAmount(orders.getPaymentAmount())
                .deliveryCharge(orders.getDeliveryCharge())
                .status(orders.getStatus())
                .build();
    }

    // DTO -> Entity
    public Orders toEntity() {

        Member member = Member.builder().id(this.member_id).build();
        Delivery delivery = Delivery.builder().id(this.delivery_id).build();
        List<OrderItem> orderItemList = new ArrayList<>();
        this.orderItemDtoList.stream().forEach(orderItemDto -> orderItemList.add(orderItemDto.toEntity()));

        return Orders.builder()
                .id(this.id)
                .member(member)
                .delivery(delivery)
                .orderItems(orderItemList)
                .paymentAmount(this.paymentAmount)
                .deliveryCharge(this.deliveryCharge)
                .status(this.status)
                .build();
    }

}