package com.finder.mypet.review.service;

import com.finder.mypet.common.advice.exception.CustomException;
import com.finder.mypet.common.response.ResponseCode;
import com.finder.mypet.review.domain.entity.Review;
import com.finder.mypet.review.domain.repository.ReviewRepository;
import com.finder.mypet.review.dto.request.ReviewRequest;
import com.finder.mypet.review.dto.response.ReviewAllInfoResponse;
import com.finder.mypet.review.dto.response.ReviewInfoResponse;
import com.finder.mypet.user.domain.entity.User;
import com.finder.mypet.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;

    @Transactional
    public void save(org.springframework.security.core.userdetails.User userDetail, ReviewRequest dto) {
        User user = userService.userDetail(userDetail);

        Review review = Review.builder()
                .writer(user)
                .shelter(dto.getShelter())
                .content(dto.getContent())
                .rating(dto.getRating())
                .build();

        reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public ReviewInfoResponse getReview(Long reviewId) {
        Review review = findByReviewId(reviewId);
        return ReviewInfoResponse.dto(review);
    }

    @Transactional(readOnly = true)
    public Page<ReviewAllInfoResponse> readAll(Integer pageNo, Long shelterId) {
        if (pageNo == null) pageNo = 1;
        Pageable pageable = PageRequest.of(pageNo - 1, 20, Sort.Direction.DESC, "rating");
        return reviewRepository.findAllByShelter(shelterId, pageable).map(ReviewAllInfoResponse::dto);
    }

    @Transactional
    public void edit(org.springframework.security.core.userdetails.User userDetail, Long reviewId, ReviewRequest reviewRequest) {
        User user = userService.userDetail(userDetail);
        Review review = findByReviewId(reviewId);

        checkWriter(user, review);
        review.updateReview(reviewRequest);
    }

    @Transactional
    public void delete(org.springframework.security.core.userdetails.User userDetail, Long reviewId) {
        User user = userService.userDetail(userDetail);
        Review review = findByReviewId(reviewId);

        checkWriter(user, review);
        reviewRepository.deleteById(reviewId);
    }

    private void checkWriter(User user, Review review) {
        if (!user.getNickname().equals(review.getWriter().getNickname())) {
            throw new CustomException(ResponseCode.NOT_AUTHORITY);
        }
    }

    public Review findByReviewId(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_REVIEW));
    }
}
