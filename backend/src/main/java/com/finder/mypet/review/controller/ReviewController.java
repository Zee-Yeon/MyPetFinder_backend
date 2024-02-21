package com.finder.mypet.review.controller;

import com.finder.mypet.board.domain.entity.Category;
import com.finder.mypet.board.dto.request.BoardRequest;
import com.finder.mypet.review.dto.request.ReviewRequest;
import com.finder.mypet.review.dto.response.ReviewInfoResponse;
import com.finder.mypet.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

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
//
//    // 게시글 전체 조회
//    @GetMapping("/boards/{category}")
//    // 카테고리, 조회수, 최신순
//    public void readAll(@PathVariable(required = false) Category category,
//                        @RequestParam(required = false, defaultValue = "0", value = "view") int view,
//                        @RequestParam(required = false, defaultValue = "id", value = "orderby") String boardId,
//                        Pageable pageable) {
//
//        System.out.println("readAll");
//    }


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
