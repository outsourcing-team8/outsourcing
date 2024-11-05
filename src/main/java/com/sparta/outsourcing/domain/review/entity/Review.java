package com.sparta.outsourcing.domain.review.entity;

import com.sparta.outsourcing.domain.order.entity.Order;
import com.sparta.outsourcing.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int star;

    @Builder
    public Review(Order order, String content, int star) {
        this.order = order;
        this.content = content;
        this.star = star;
    }
}
