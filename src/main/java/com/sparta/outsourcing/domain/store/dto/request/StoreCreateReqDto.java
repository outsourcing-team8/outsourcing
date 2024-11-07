package com.sparta.outsourcing.domain.store.dto.request;

import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class StoreCreateReqDto {

    @NotBlank(message = "가게 이름을 입력해주세요.")
    private String name;

    @NotNull(message = "영업 시작 시간을 입력해주세요.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime openAt;

    @NotNull(message = "영업 마감 시간을 입력해주세요.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime closedAt;

    @NotNull(message = "최소 주문 가격을 입력해주세요.")
    @Range(min = 100, max = 100_000_000, message = "최소 100원부터 최대 1억원 까지만 입력해주세요.")
    private int minPrice;

    public Store toEntity(User owner) {
        return Store.builder()
                .owner(owner)
                .name(this.name)
                .openAt(this.openAt)
                .closedAt(this.closedAt)
                .minPrice(this.minPrice)
                .build();
    }
}
