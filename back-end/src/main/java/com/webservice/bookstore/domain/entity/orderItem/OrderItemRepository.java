package com.webservice.bookstore.domain.entity.orderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("select oi from OrderItem oi join fetch oi.item i join fetch i.category c where c.id = :id")
    OrderItem findByCategoryId(@Param("id") Long id);
}
