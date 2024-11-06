package com.sparta.outsourcing.domain.order.repository;

import com.sparta.outsourcing.domain.order.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * FROM orders " +
            "WHERE is_deleted = false AND user_id = :loginUserId", nativeQuery = true)
    Slice<Order> findUserOrderHistory(@Param("loginUserId") Long loginUserId, Pageable pageable);

    @Query(value = "SELECT o FROM Order AS o WHERE o.orderId = :id")
    @EntityGraph(attributePaths = {"user", "menu"})
    Optional<Order> findByIdEntityGraph(@Param("id") Long id);
}
