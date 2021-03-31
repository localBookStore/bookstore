package com.webservice.bookstore.domain.entity.delivery;

import com.webservice.bookstore.domain.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class Delivery extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    @Enumerated(EnumType.STRING)
    private DeliveryEnum status;

    public void cancel() {
        this.status = DeliveryEnum.CANCEL;
    }
}