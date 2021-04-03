package com.webservice.bookstore.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webservice.bookstore.domain.entity.delivery.Delivery;
import com.webservice.bookstore.domain.entity.delivery.DeliveryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class DeliveryDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Default {
        private Long id;
        private String address;
        private DeliveryEnum status;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime modifiedDate;

        // Entity -> DTO
        public static Default of(Delivery delivery) {
            return Default.builder()
                    .id(delivery.getId())
                    .address(delivery.getAddress())
                    .status(delivery.getStatus())
                    .modifiedDate(delivery.getModifiedDate())
                    .build();
        }

        // DTO -> Entity
        public Delivery toEntity() {
            return Delivery.builder()
                    .id(this.id)
                    .address(this.address)
                    .status(this.status)
                    .build();
        }

        // Default -> Response
        public Response toResponse() {
            return Response.builder()
                    .address(this.address)
                    .status(this.status)
                    .modifiedDate(this.modifiedDate)
                    .build();
        }

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private String address;
        private DeliveryEnum status;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime modifiedDate;
    }

}
