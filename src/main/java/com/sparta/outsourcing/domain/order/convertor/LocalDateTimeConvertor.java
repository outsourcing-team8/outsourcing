package com.sparta.outsourcing.domain.order.convertor;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.common.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class LocalDateTimeConvertor {
    public LocalDateTime convertStringToLocalDateTime(String selectedDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(selectedDate, formatter);
        } catch (DateTimeParseException e) {
            throw new CustomApiException(ErrorCode.SELECTED_DATE_NOT_VALID);
        }
    }
}
