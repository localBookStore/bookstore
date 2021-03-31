package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.orderItem.OrderItem;
import com.webservice.bookstore.domain.entity.orderItem.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public Optional<OrderItem> findById(Long id) {
        Optional<OrderItem> orderItem = this.orderItemRepository.findById(id);
        return orderItem;
    }

    public OrderItem findByCategoryId (Long id) {
        return orderItemRepository.findByCategoryId(id);
    }

}
