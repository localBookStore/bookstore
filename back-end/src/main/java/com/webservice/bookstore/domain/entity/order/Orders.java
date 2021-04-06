package com.webservice.bookstore.domain.entity.order;


import com.webservice.bookstore.domain.entity.BaseTimeEntity;
import com.webservice.bookstore.domain.entity.coupon.Coupon;
import com.webservice.bookstore.domain.entity.delivery.Delivery;
import com.webservice.bookstore.domain.entity.delivery.DeliveryEnum;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.orderItem.OrderItem;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"member", "delivery", "orderItems"})
@EqualsAndHashCode(of = "id")
public class Orders extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Builder.Default
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
//    private Set<OrderItem> orderItems = new HashSet<>();    // MultipleBagFetchException 발생 방지를 위해 List -> Set

    private int paymentAmount;

    private Integer deliveryCharge;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
        coupon.setOrder(this);
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
    public static Orders createOrder(Member member, Coupon coupon, List<OrderItem> orderItemList) {

        // 배송 정보 생성
        Delivery delivery = Delivery.builder()
                                    .address(member.getAddress())
                                    .status(DeliveryEnum.READY)
                                    .build();

        double paymentAmount = orderItemList.stream()
                                            .mapToDouble(orderItem -> (orderItem.getOrderPrice() * orderItem.getOrderCount()))
                                            .sum();

        if(coupon != null) {
            paymentAmount = (paymentAmount * ((100 - coupon.getDiscountRate()) / (double) 100));
        }

        // Builder 패턴 사용법 주의사항 :
        Orders order = Orders.builder()
                             .member(member) // 결제자 정보 등록
                             .paymentAmount((int) paymentAmount)
                             .deliveryCharge(2500) // 배송비 초기화
                             .coupon(coupon)
                             .build();

        // Orders, Delivery 엔티티 간 연관 데이터 주입
        order.addDelivery(delivery);
        orderItemList.forEach(order::addOrderItem);

        return order;
    }

    /*
    주문 수락
    */
    public void accept() {

        checkDeliveryStatus();

        this.delivery.shipping();
    }

    /*
    주문 취소
    */
    public void cancel() {

        checkDeliveryStatus();

        this.delivery.cancel();
        orderItems.forEach(OrderItem::cancel);
    }

    private void checkDeliveryStatus() {
        // 배송(delivery) 상태가 이미 완료(COMPLETED) 또는 배송 중(SHIPPING)인 경우, 예외 발생
        if(this.delivery.getStatus().equals(DeliveryEnum.SHIPPING)) {
            throw new IllegalStateException("이미 배송 중인 상태이므로 취소가 불가능합니다.");
        } else if(this.delivery.getStatus().equals(DeliveryEnum.COMPLETED)) {
            throw new IllegalStateException("이미 배송이 완료된 상태입니다.");
        }
    }

}