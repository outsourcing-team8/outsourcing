package com.sparta.outsourcing.domain.menu.entity;

import com.sparta.outsourcing.common.entity.BaseEntity;
import com.sparta.outsourcing.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    private boolean deleted = Boolean.FALSE;


    @Builder
    public Menu(Store store, String name, int price, boolean deleted) {
        this.store = store;
        this.name = name;
        this.price = price;
        this.deleted = deleted;
    }

    public void update(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public void deleted() {
        this.deleted = Boolean.TRUE;
    }

}