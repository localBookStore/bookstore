package com.webservice.bookstore.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webservice.bookstore.domain.entity.delivery.Delivery;
import com.webservice.bookstore.domain.entity.delivery.DeliveryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
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
        private LocalDateTime createdDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime modifiedDate;

        // Entity -> DTO
        public static Default of(Delivery delivery) {
            return Default.builder()
                          .id(delivery.getId())
                          .address(delivery.getAddress())
                          .status(delivery.getStatus())
                          .createdDate(delivery.getCreatedDate())
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

            long progress = 0;  // 배송 진행률 계산
            if(!this.status.equals(DeliveryEnum.CANCEL) && !this.status.equals(DeliveryEnum.READY)) {
                if(this.status == DeliveryEnum.COMPLETED) {
                    progress = 100;
                } else {
                    long duration = Duration.between(this.modifiedDate, LocalDateTime.now()).getSeconds();
                    progress = (long) (duration / (double)(60*60*24*3)) * 100;
                }
            }

            return Response.builder()
                           .address(this.address)
                           .status(this.status)
                           .createdDate(this.createdDate)
                           .modifiedDate(this.modifiedDate)
                           .progress(progress)
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
        private LocalDateTime createdDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime modifiedDate;
        private Long progress;
    }

}
