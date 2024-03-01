package com.finder.mypet.review.controller;

import com.finder.mypet.common.response.Response;
import com.finder.mypet.review.dto.request.ReviewRequest;
import com.finder.mypet.review.dto.response.ReviewAllInfoResponse;
import com.finder.mypet.review.dto.response.ReviewInfoResponse;
import com.finder.mypet.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;


import static com.finder.mypet.common.response.ResponseCode.*;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성 [ㅇ]
    @PostMapping("/user/review")
    public ResponseEntity<?> save(@AuthenticationPrincipal User user, @RequestBody ReviewRequest reviewRequest) {
        reviewService.save(user, reviewRequest);
        return new ResponseEntity<>(Response.create(CREATE_REVIEW, null), CREATE_REVIEW.getHttpStatus());
    }

    // 리뷰 상세보기 (비회원도 가능) [ㅇ]
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<?> getBoard(@PathVariable("reviewId") Long reviewId) {
        ReviewInfoResponse review = reviewService.getReview(reviewId);
        return new ResponseEntity<>(Response.create(GET_REVIEW, review), GET_REVIEW.getHttpStatus());
    }

    // 보호소 리뷰 전체 조회(rating 높은 기준으로 정렬) [ㅇ]
    @GetMapping("/reviews/{shelter}")
    public ResponseEntity<?> readAll(@RequestParam(required = false, defaultValue = "1", value = "page") Integer pageNo,
                                     @PathVariable(name = "shelter", required = false) Long shelterId) {
        Page<ReviewAllInfoResponse> reviewList =  reviewService.readAll(pageNo, shelterId);
        return new ResponseEntity<>(Response.create(GET_REVIEWS, reviewList), GET_REVIEWS.getHttpStatus());
    }

    // 리뷰 수정 [ㅇ]
    @PutMapping("/user/review/{reviewId}")
    public ResponseEntity<?> edit(@AuthenticationPrincipal User user, @PathVariable("reviewId") Long reviewId, @RequestBody ReviewRequest reviewRequest) {
        reviewService.edit(user, reviewId, reviewRequest);
        return new ResponseEntity<>(Response.create(EDIT_REVIEW, null), EDIT_REVIEW.getHttpStatus());
    }

    // 리뷰 삭제 [ㅇ]
    @DeleteMapping("/user/review/{reviewId}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user, @PathVariable("reviewId") Long reviewId){
        reviewService.delete(user, reviewId);
        return new ResponseEntity<>(Response.create(DELETE_REVIEW, null), DELETE_REVIEW.getHttpStatus());
    }
}
