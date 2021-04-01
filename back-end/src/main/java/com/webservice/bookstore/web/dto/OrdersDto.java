package com.webservice.bookstore.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webservice.bookstore.domain.entity.delivery.Delivery;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.order.Orders;
import com.webservice.bookstore.domain.entity.order.OrdersEnum;
import com.webservice.bookstore.domain.entity.orderItem.OrderItem;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrdersDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Default {
        private Long id;
        private Long member_id;
        private Long delivery_id;
        private List<OrderItemDto.Default> orderItemDtoList = new ArrayList<>();
        private Integer paymentAmount;
        private Integer deliveryCharge;
        private OrdersEnum status;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createDate;

        // Entity -> DTO
        public static Default of(Orders orders) {

            List<OrderItemDto.Default> orderItemDtoList = new ArrayList<>();
            orders.getOrderItems().stream().forEach(orderItem -> orderItemDtoList.add(OrderItemDto.Default.of(orderItem)));

            return Default.builder()
                    .id(orders.getId())
                    .member_id(orders.getMember().getId())
                    .delivery_id(orders.getDelivery().getId())
                    .orderItemDtoList(orderItemDtoList)
                    .paymentAmount(orders.getPaymentAmount())
                    .deliveryCharge(orders.getDeliveryCharge())
                    .status(orders.getStatus())
                    .createDate(orders.getCreatedDate())
                    .build();
        }

        // DTO -> Entity
        public Orders toEntity() {

            Member member = Member.builder().id(this.member_id).build();
            Delivery delivery = Delivery.builder().id(this.delivery_id).build();
            List<OrderItem> orderItemList = new ArrayList<>();    // Orders : MultipleBagFetchException 발생 방지를 위해 List -> Set
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

        // Default -> Response
        public Response toResponse() {

            List<OrderItemDto.Response> responseList = this.orderItemDtoList.stream()
                                                                    .map(orderItemDto -> orderItemDto.toResponse())
                                                                    .collect(Collectors.toList());

            return Response.builder()
                    .id(this.id)
                    .member_id(this.member_id)
                    .delivery_id(this.delivery_id)
                    .orderItemList(responseList)
                    .paymentAmount(this.paymentAmount)
                    .deliveryCharge(this.deliveryCharge)
                    .status(this.status)
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
        private Long delivery_id;
        private List<OrderItemDto.Response> orderItemList = new ArrayList<>();
        private Integer paymentAmount;
        private Integer deliveryCharge;
        private OrdersEnum status;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createDate;
    }
}