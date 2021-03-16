package com.webservice.bookstore.domain.entity.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository  extends JpaRepository<Orders, Long> {
}
