package com.sparta.outsourcing.domain.order.validator;

import com.sparta.outsourcing.common.exception.CustomApiException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class OrderValidatorTest {
    @InjectMocks
    private OrderValidator orderValidator;

    @Test
    @DisplayName("최소 주문 금액을 만족하지 않는 경우")
    void checkMinPriceFail() {
        // given
        int storeMinPrice = 3000;
        int menuPrice = 1000;
        int amount = 1;

        // when & then
        assertThrows(CustomApiException.class, () ->
            orderValidator.checkMinPrice(storeMinPrice, menuPrice, amount)
        );
    }

    @Test
    @DisplayName("가게영업시간(19시~21시) 외에 주문하는 경우")
    void checkBusinessHoursStoreNotOpen1() {
        // given
        LocalTime storeOpenAt = LocalTime.of(19, 0);
        LocalTime storeClosedAt = LocalTime.of(21, 0);

        // when & then
        assertThrows(CustomApiException.class, () ->
                orderValidator.checkBusinessHours(storeOpenAt, storeClosedAt)
        );
    }

    @Test
    @DisplayName("가게영업시간(18시~06시) 외에 주문하는 경우")
    void checkBusinessHoursStoreNotOpen2() {
        // given
        LocalTime storeOpenAt = LocalTime.of(18, 0);
        LocalTime storeClosedAt = LocalTime.of(6, 0);

        // when & then
        assertThrows(CustomApiException.class, () ->
                orderValidator.checkBusinessHours(storeOpenAt, storeClosedAt)
        );
    }
}