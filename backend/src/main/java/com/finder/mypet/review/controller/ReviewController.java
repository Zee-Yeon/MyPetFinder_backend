package com.finder.mypet.review.controller;

import com.finder.mypet.review.dto.request.ReviewRequest;
import com.finder.mypet.review.dto.response.ReviewAllInfoResponse;
import com.finder.mypet.review.dto.response.ReviewInfoResponse;
import com.finder.mypet.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성
    @PostMapping("/user/review")
    public ResponseEntity<?> save(@AuthenticationPrincipal User user, @RequestBody ReviewRequest dto) {
        String userId = user.getUsername();

        reviewService.save(userId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 리뷰 상세보기 (비회원도 가능)
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<?> getBoard(@PathVariable("reviewId") Long reviewId) {
        ReviewInfoResponse review = reviewService.getReview(reviewId);

        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    // 리뷰 전체 조회(rating 높은 기준으로 정렬)
    @GetMapping("/reviews/{shelter}")
    public ResponseEntity<?> readAll(@RequestParam(required = false, defaultValue = "1", value = "page") Integer pageNo,
                                     @PathVariable(name = "shelter", required = false) Long shelterId) {

        if (pageNo == null) pageNo = 1;

        Page<ReviewAllInfoResponse> reviewList =  reviewService.readAll(pageNo, shelterId);

        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }


    // 리뷰 수정
    @PutMapping("/user/review/{reviewId}")
    public ResponseEntity<?> edit(@AuthenticationPrincipal User user, @PathVariable("reviewId") Long reviewId, @RequestBody ReviewRequest dto){
        String userId = user.getUsername();

        reviewService.edit(userId, reviewId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 리뷰 삭제
    @DeleteMapping("/user/review/{reviewId}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user, @PathVariable("reviewId") Long reviewId){
        String userId = user.getUsername();

        reviewService.delete(userId, reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
