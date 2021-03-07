package com.webservice.bookstore.domain.entity.cart;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    /*
    EntityGraph는 JPA가 어떤 엔티티를 불러올 때,
    이 엔티티와 관계된 엔티티를 불러올 것인지에 대한 정보를 제공.
    */
    @EntityGraph(value = "Cart.member")
    List<Cart> findByMemberId(Long id);
}