package com.sparta.outsourcing.domain.review.repository;

import com.sparta.outsourcing.domain.order.entity.Order;
import com.sparta.outsourcing.domain.review.entity.Review;
import com.sparta.outsourcing.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByOrder(Order order);
    Page<Review> findByOrder_Menu_StoreAndStarBetween(Store store, int minStar, int maxStar, Pageable pageable);
}
