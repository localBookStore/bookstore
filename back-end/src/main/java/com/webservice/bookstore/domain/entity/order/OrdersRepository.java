package com.webservice.bookstore.domain.entity.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersRepository  extends JpaRepository<Orders, Long> {

//    List<Orders> findByMemberId(Long member_id);
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("select o from Orders o where o.member.id = :member_id")
    List<Orders> findByMemberId(@Param(value = "member_id") Long member_id);

}
