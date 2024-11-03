package com.sparta.outsourcing.domain.store.repository;

import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Long countByUser(User user);
    Optional<Store> findByName(String name);
}
