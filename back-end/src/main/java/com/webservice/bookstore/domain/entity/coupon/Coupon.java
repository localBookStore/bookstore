package com.webservice.bookstore.domain.entity.coupon;


import com.webservice.bookstore.domain.entity.category.Category;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.orderItem.OrderItem;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"orderItem", "category"})
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int discountRate;
    private LocalDate endDate;

    @Builder.Default
    private Boolean isUsed = false;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_item_id")
//    private OrderItem orderItem;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category_id")
//    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


//    public void addOrderItem(OrderItem orderItem) {
//        this.orderItem = orderItem;
//    }

    public void isUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }
}
