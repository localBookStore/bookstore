package com.webservice.bookstore.domain.entity.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByMemberId(Long member_id);

    Optional<Cart> findByMemberIdAndItemId(Long member_id, Long item_id);

}