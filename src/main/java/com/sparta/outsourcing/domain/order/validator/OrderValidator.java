package com.sparta.outsourcing.domain.order.validator;

import com.sparta.outsourcing.common.exception.CustomApiException;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

import static com.sparta.outsourcing.common.exception.ErrorCode.MIN_PRICE_NOT_MET;
import static com.sparta.outsourcing.common.exception.ErrorCode.NOT_BUSINESS_HOURS;

@Component
public class OrderValidator {
    public void checkMinPrice(int storeMinPrice, int menuPrice, int amount) {
        if (storeMinPrice > menuPrice * amount) throw new CustomApiException(MIN_PRICE_NOT_MET);
    }

    public void checkBusinessHours(LocalTime openAt, LocalTime closedAt, LocalTime orderedAt) {
        if (openAt.isBefore(closedAt)) {
            if (orderedAt.isBefore(openAt) || orderedAt.isAfter(closedAt))
                throw new CustomApiException(NOT_BUSINESS_HOURS);

        } else if (openAt.isAfter(closedAt)) {
            if (orderedAt.isBefore(openAt) && orderedAt.isAfter(closedAt))
                throw new CustomApiException(NOT_BUSINESS_HOURS);
        }
    }
}
