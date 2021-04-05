package com.webservice.bookstore.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.order.Orders;
import com.webservice.bookstore.domain.entity.orderItem.OrderItem;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
        private DeliveryDto.Default deliveryDto;
        private List<OrderItemDto.Default> orderItemDtoList = new ArrayList<>();
        private Integer paymentAmount;
        private Integer deliveryCharge;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createDate;

        // Entity -> DTO
        public static Default of(Orders orders) {

            DeliveryDto.Default deliveryDto = DeliveryDto.Default.builder()
                                                                 .id(orders.getDelivery().getId())
                                                                 .address(orders.getDelivery().getAddress())
                                                                 .status(orders.getDelivery().getStatus())
                                                                 .createdDate(orders.getDelivery().getModifiedDate())
                                                                 .modifiedDate(orders.getDelivery().getModifiedDate())
                                                                 .build();
            List<OrderItemDto.Default> orderItemDtoList = new ArrayList<>();
            orders.getOrderItems().stream().forEach(orderItem -> orderItemDtoList.add(OrderItemDto.Default.of(orderItem)));

            return Default.builder()
                          .id(orders.getId())
                          .member_id(orders.getMember().getId())
                          .deliveryDto(deliveryDto)
                          .orderItemDtoList(orderItemDtoList)
                          .paymentAmount(orders.getPaymentAmount())
                          .deliveryCharge(orders.getDeliveryCharge())
                          .createDate(orders.getCreatedDate())
                          .build();
        }

        // DTO -> Entity
        public Orders toEntity() {

            Member member = Member.builder().id(this.member_id).build();
            List<OrderItem> orderItemList = new ArrayList<>();    // Orders : MultipleBagFetchException 발생 방지를 위해 List -> Set
            this.orderItemDtoList.stream().forEach(orderItemDto -> orderItemList.add(orderItemDto.toEntity()));

            return Orders.builder()
                         .id(this.id)
                         .member(member)
                         .delivery(this.deliveryDto.toEntity())
                         .orderItems(orderItemList)
                         .paymentAmount(this.paymentAmount)
                         .deliveryCharge(this.deliveryCharge)
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
                           .delivery(this.deliveryDto.toResponse())
                           .orderItemList(responseList)
                           .paymentAmount(this.paymentAmount)
                           .deliveryCharge(this.deliveryCharge)
                           .createDate(this.createDate)
                           .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderRequest {
        @Size(min = 1, message = "주문할 아이템을 선택해주세요.")
        private List<CartDto.Default> orderList;
        @NotBlank(message = "주소를 입력해주세요.")
        private String address;
        private Long coupon_id;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private Long member_id;
        private DeliveryDto.Response delivery;
        private List<OrderItemDto.Response> orderItemList = new ArrayList<>();
        private Integer paymentAmount;
        private Integer deliveryCharge;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createDate;
    }
}