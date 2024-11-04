package com.sparta.outsourcing.domain.review.controller;

import com.sparta.outsourcing.domain.review.dto.request.ReviewCreateReqDto;
import com.sparta.outsourcing.domain.review.dto.response.ReviewCreateRespDto;
import com.sparta.outsourcing.domain.review.service.ReviewService;
import com.sparta.outsourcing.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/orders/{orderId}")
    public ResponseEntity<ReviewCreateRespDto> createReview(
            @AuthenticationPrincipal User user,
            @PathVariable("orderId") Long orderId,
            @RequestBody ReviewCreateReqDto reqDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewService.createReview(user, orderId, reqDto));
    }
}
