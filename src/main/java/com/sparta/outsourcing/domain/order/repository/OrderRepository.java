package com.sparta.outsourcing.domain.order.repository;

import com.sparta.outsourcing.domain.order.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * FROM orders " +
            "WHERE is_deleted = false AND user_id = :loginUserId", nativeQuery = true)
    Slice<Order> findUserOrderHistory(@Param("loginUserId") Long loginUserId, Pageable pageable);
}
