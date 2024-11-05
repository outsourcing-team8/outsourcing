package com.sparta.outsourcing.domain.order.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Builder
public class OrderListForOwnerRespDto {
    private List<OrderFindForOwnerRespDto> orderList;

    private Pageable pageable;

    public static OrderListForOwnerRespDto make(List<OrderFindForOwnerRespDto> orderList, Pageable pageable) {
        return OrderListForOwnerRespDto.builder()
                .orderList(orderList)
                .pageable(pageable)
                .build();
    }
}
