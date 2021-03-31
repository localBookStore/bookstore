package com.webservice.bookstore.domain.entity.orderItem;

import com.webservice.bookstore.domain.entity.coupon.Coupon;
import com.webservice.bookstore.domain.entity.BaseTimeEntity;
import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.order.Orders;
import com.webservice.bookstore.web.dto.OrderItemDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class OrderItem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

//    @OneToMany(mappedBy = "orderItem")
//    private List<Coupon> coupons;

    private Integer orderCount;

    private Integer orderPrice;


    /*
    주문 생성 로직
    */
    public void addOrder(Orders orders) {
        this.orders = orders;
    }

    public int couponApplyOrderPrice(Coupon coupon) {
        int discountRate = coupon.getDiscountRate();
        Integer sum = orderPrice * ((100-discountRate)/100);
        return sum;
    }

    public void validateCoupon(Coupon coupon) {

    }

    /*
    주문아이템 생성 메소드
    */
    public static List<OrderItem> createOrderItem(List<Item> itemList,
                                                  List<OrderItemDto> orderItemDtoList) {

        List<OrderItem> orderItemList = new ArrayList<>();
        for(int i = 0; i < itemList.size(); i++) {
            OrderItem orderItem = OrderItem.builder()
                                           .item(itemList.get(i))
                                           .orderPrice(itemList.get(i).getPrice())
                                           .orderCount(orderItemDtoList.get(i).getOrderCount())
                                           .build();
            orderItemList.add(orderItem);

            // (주문 생성 시) 재고량(stock) 감소
            itemList.get(i).removeStockQuantity(orderItemDtoList.get(i).getOrderCount());
        }
        return orderItemList;
    }

    /*
    주문 상품 주문 취소
    */
    public void cancel() {
        this.item.addStockQuantity(this.orderCount);
    }
}
