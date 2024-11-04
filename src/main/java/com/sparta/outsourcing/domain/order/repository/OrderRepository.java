package com.sparta.outsourcing.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.outsourcing.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
