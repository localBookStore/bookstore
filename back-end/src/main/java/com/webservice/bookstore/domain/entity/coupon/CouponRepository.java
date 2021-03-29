package com.webservice.bookstore.domain.entity.coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("select c from Coupon c join fetch c.category cc where cc.id = :id")
    List<Coupon> findCouponList(@Param("id") Long id);
}
