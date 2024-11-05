package com.sparta.outsourcing.domain.review.repository;

import com.sparta.outsourcing.domain.order.entity.Order;
import com.sparta.outsourcing.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByOrder(Order order);
}
