package com.sparta.outsourcing.domain.order.validator;

import com.sparta.outsourcing.common.exception.CustomApiException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}