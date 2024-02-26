package com.finder.mypet.review.service;

import com.finder.mypet.common.advice.exception.CustomException;
import com.finder.mypet.common.response.ResponseCode;
import com.finder.mypet.review.domain.entity.Review;
import com.finder.mypet.review.domain.repository.ReviewRepository;
import com.finder.mypet.review.dto.request.ReviewRequest;
import com.finder.mypet.review.dto.response.ReviewAllInfoResponse;
import com.finder.mypet.review.dto.response.ReviewInfoResponse;
import com.finder.mypet.user.domain.entity.User;
import com.finder.mypet.user.domain.repository.UserRepository;
import com.finder.mypet.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.FindException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;

    @Transactional
    public void save(org.springframework.security.core.userdetails.User userDetail, ReviewRequest dto) {
        String userId = userService.userDetail(userDetail);
        User user = userService.findByUserId(userId);

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

        Pageable pageable = PageRequest.of(pageNo - 1, 20, Sort.Direction.DESC, "rating");
        Page<ReviewAllInfoResponse> review = reviewRepository.findAllByShelter(shelterId, pageable).map(ReviewAllInfoResponse::dto);

        return review;
    }

    @Transactional
    public void edit(org.springframework.security.core.userdetails.User userDetail, Long reviewId, ReviewRequest dto) {
        String userId = userService.userDetail(userDetail);
        userService.findByUserId(userId);

        Review review = findByReviewId(reviewId);

        review.setContent(dto.getContent());
        review.setRating(dto.getRating());
    }

    @Transactional
    public void delete(org.springframework.security.core.userdetails.User userDetail, Long reviewId) {
        String userId = userService.userDetail(userDetail);
        User user = userService.findByUserId(userId);
        Review review = findByReviewId(reviewId);

        if (!user.getNickname().equals(review.getWriter().getNickname())) {
            throw new CustomException(ResponseCode.NOT_AUTHORITY);
        }
        reviewRepository.deleteById(reviewId);
    }

    public Review findByReviewId(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_REVIEW));
    }
}
