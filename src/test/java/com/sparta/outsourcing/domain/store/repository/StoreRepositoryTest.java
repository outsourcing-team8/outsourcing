package com.sparta.outsourcing.domain.store.repository;

import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.user.entity.User;
import com.sparta.outsourcing.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class StoreRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StoreRepository storeRepository;

    LocalTime openAt = LocalTime.of(14, 0);
    LocalTime closedAt = LocalTime.of(15, 0);

    @Test
    @DisplayName("delete Test")
    void deleteTest() {
        //given
        Store store = Store.builder().name("치킨집").openAt(openAt).closedAt(closedAt).build();
        store = storeRepository.save(store);
        Long storeId = store.getStoreId();
        //when
        storeRepository.delete(store);

        //then
        assertNotNull(storeRepository.findById(storeId));
        assertEquals(storeRepository.findByStoreIdAndDeletedIsFalse(storeId), Optional.empty());
    }
}
