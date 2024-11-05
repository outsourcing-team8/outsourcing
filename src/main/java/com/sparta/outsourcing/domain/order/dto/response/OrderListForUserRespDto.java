package com.sparta.outsourcing.domain.order.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Builder
public class OrderListForUserRespDto {
    List<OrderFindForUserRespDto> orderList;

    private Pageable pageable;

    public static OrderListForUserRespDto make(List<OrderFindForUserRespDto> orderList, Pageable pageable) {
        return OrderListForUserRespDto.builder()
                .orderList(orderList)
                .pageable(pageable)
                .build();
    }
}
