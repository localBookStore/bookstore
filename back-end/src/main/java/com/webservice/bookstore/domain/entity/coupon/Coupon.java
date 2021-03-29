package com.webservice.bookstore.domain.entity.coupon;


import com.webservice.bookstore.domain.entity.category.Category;
import com.webservice.bookstore.domain.entity.order.Orders;
import com.webservice.bookstore.domain.entity.orderItem.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int discountRate;
    private LocalDate endDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    public void addOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

}
