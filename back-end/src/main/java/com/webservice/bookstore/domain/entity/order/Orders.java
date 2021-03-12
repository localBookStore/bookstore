package com.webservice.bookstore.domain.entity.order;

import com.webservice.bookstore.domain.entity.delivery.Delivery;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.orderItem.OrderItem;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"member","delivery"})
@EqualsAndHashCode(of = "id")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    //private List<Coupon> coupons;

    private Integer paymentAmount;

    private Integer deliveryCharge;

    private OrdersEnum status;

}
