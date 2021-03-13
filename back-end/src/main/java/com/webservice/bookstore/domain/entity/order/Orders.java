package com.webservice.bookstore.domain.entity.order;

import com.webservice.bookstore.domain.entity.delivery.Delivery;
import com.webservice.bookstore.domain.entity.delivery.DeliveryEnum;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.orderItem.OrderItem;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    //private List<Coupon> coupons;

    private Integer paymentAmount;

    private Integer deliveryCharge;

    @Enumerated(EnumType.STRING)
    private OrdersEnum status;


    private void updateOrderStatus(OrdersEnum status) {
        this.status = status;
    }

    // 연관관계 편의 메소드
    public void addDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.addOrder(this);
    }

    // 주문 생성 메소드
    public static Orders createOrder(Member member,
                                     List<OrderItem> orderItemList) {

        // 배송 정보 생성
        Delivery delivery = Delivery.builder()
                                    .address(member.getAddress())
                                    .status(DeliveryEnum.START)
                                    .build();

        // Builder 패턴 사용법 주의사항 :
        Orders order = Orders.builder()
                .member(member) // 결제자 정보 등록
                .orderItems(new ArrayList<>()) // 'builder 패턴 사용 시 주의사항 숙지할 것'
                .paymentAmount(orderItemList.stream().mapToInt(OrderItem::getOrderPrice).sum()) // 결제 금액(배송비 별도)
                .deliveryCharge(2500) // 배송비 초기화
                .status(OrdersEnum.ORDER) // 주문 상태 초기화
                .build();

        // Orders/Delivery 엔티티 간 양방향 연관관계 데이터 주입
        order.addDelivery(delivery);
        for(OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }

        return order;
    }

}