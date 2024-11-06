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

    @Test
    @DisplayName("count Test")
    void countTest() {
        //given
        User owner = User.builder().email("test@test.com").password("123456").nickname("test").phoneNumber("111-1111-1111").build();
        owner = userRepository.save(owner);

        int storeSize = 10;
        for(long i = 1; i <= storeSize; i++) {
            Store store = Store.builder().owner(owner).name("치킨집").openAt(openAt).closedAt(closedAt).build();
            storeRepository.save(store);
        }

        //when
        int count = storeRepository.countByOwnerAndDeletedIsFalse(owner);

        //then
        assertEquals(count, storeSize);
    }

    @Test
    @DisplayName("count Test 2")
    void countTest2() {
        //given
        User owner1 = User.builder().email("test@test.com").password("123456").nickname("test").phoneNumber("111-1111-1111").build();
        User owner2 = User.builder().email("test@test.com").password("123456").nickname("test").phoneNumber("111-1111-1111").build();
        owner1 = userRepository.save(owner1);
        owner2 = userRepository.save(owner2);

        int owner1Stores = 7;
        int owner2Stores = 13;
        for(long i = 1; i <= owner1Stores; i++) {
            Store store = Store.builder().owner(owner1).name("치킨집").openAt(openAt).closedAt(closedAt).build();
            storeRepository.save(store);
        }
        for(long i = 1; i <= owner2Stores; i++) {
            Store store = Store.builder().owner(owner2).name("치킨집").openAt(openAt).closedAt(closedAt).build();
            storeRepository.save(store);
        }

        //when
        int count = storeRepository.countByOwnerAndDeletedIsFalse(owner1);

        //then
        assertEquals(count, owner1Stores);
    }
}
