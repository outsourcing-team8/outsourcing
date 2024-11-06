package com.sparta.outsourcing.domain.store.repository;

import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    int countByOwnerAndDeletedIsFalse(User owner);
    Optional<Store> findByName(String name);
    Page<Store> findAllByDeletedIsFalse(Pageable pageable);
    Page<Store> findAllByDeletedIsFalseAndNameContaining(String name, Pageable pageable);
    Optional<Store> findByStoreIdAndDeletedIsFalse(Long storeId);
}
