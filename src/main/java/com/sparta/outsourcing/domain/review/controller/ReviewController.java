package com.sparta.outsourcing.domain.review.controller;

import com.sparta.outsourcing.common.security.LoginUser;
import com.sparta.outsourcing.domain.review.dto.request.ReviewCreateReqDto;
import com.sparta.outsourcing.domain.review.dto.request.ReviewGetReqDto;
import com.sparta.outsourcing.domain.review.dto.response.ReviewCreateRespDto;
import com.sparta.outsourcing.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable("orderId") Long orderId,
            @RequestBody @Valid ReviewCreateReqDto reqDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewService.createReview(loginUser.getUser().getUserId(), orderId, reqDto));
    }

    @GetMapping("/stores/{storeId}")
    public ResponseEntity<Page<ReviewCreateRespDto>> getStoreReviews(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable("storeId") Long storeId,
            @RequestBody ReviewGetReqDto reqDto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.getStoreReviews(pageable, storeId, reqDto));
    }
}
