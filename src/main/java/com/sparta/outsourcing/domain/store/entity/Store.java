package com.sparta.outsourcing.domain.store.entity;

import com.sparta.outsourcing.common.entity.BaseEntity;
import com.sparta.outsourcing.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalTime openAt;

    @Column(nullable = false)
    private LocalTime closedAt;

    @Column(nullable = false)
    private int minPrice;

    private boolean deleted = Boolean.FALSE;

    @Builder
    public Store(User owner, String name, LocalTime openAt, LocalTime closedAt, int minPrice) {
        this.owner = owner;
        this.name = name;
        this.openAt = openAt;
        this.closedAt = closedAt;
        this.minPrice = minPrice;
    }

    public void update(LocalTime openAt, LocalTime closedAt, int minPrice) {
        this.openAt = openAt;
        this.closedAt = closedAt;
        this.minPrice = minPrice;
    }
}
