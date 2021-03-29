package com.webservice.bookstore.domain.entity.coupon;


import com.webservice.bookstore.domain.entity.category.Category;
import com.webservice.bookstore.domain.entity.order.Orders;
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


    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;



}
