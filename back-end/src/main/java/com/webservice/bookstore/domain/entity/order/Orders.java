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

    @Enumerated(EnumType.STRING)
    private OrdersEnum status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
        coupon.setOrder(this);
    }

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
    public static Orders createOrder(Member member, Coupon coupon, List<OrderItem> orderItemList) {

        // 배송 정보 생성
        Delivery delivery = Delivery.builder()
                .address(member.getAddress())
                .status(DeliveryEnum.START)
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
                             .status(OrdersEnum.ORDER) // 주문 상태 초기화
                             .build();

        // Orders, Delivery 엔티티 간 연관 데이터 주입
        order.addDelivery(delivery);
        orderItemList.stream().forEach(orderItem -> order.addOrderItem(orderItem));

        return order;
    }

    /*
    주문 취소
    */
    public void cancel() {
        // 배속(delivery) 상태가 이미 완료(complete)된 상태일 경우, 예외상태 반환
        if(delivery.getStatus() != DeliveryEnum.START) {
            throw new IllegalStateException("이미 배송된 상태이므로, 취소가 불가능 합니다.");
        }

        this.delivery.cancel();
        this.updateOrderStatus(OrdersEnum.CANCEL);
        orderItems.forEach(OrderItem::cancel);
    }

}