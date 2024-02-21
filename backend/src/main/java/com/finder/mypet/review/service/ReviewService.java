package com.finder.mypet.review.service;

import com.finder.mypet.review.domain.entity.Review;
import com.finder.mypet.review.domain.repository.ReviewRepository;
import com.finder.mypet.review.dto.request.ReviewRequest;
import com.finder.mypet.review.dto.response.ReviewAllInfoResponse;
import com.finder.mypet.review.dto.response.ReviewInfoResponse;
import com.finder.mypet.user.domain.entity.User;
import com.finder.mypet.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(String userId, ReviewRequest dto) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        Review review = Review.builder()
                .writer(user)
                .shelter(dto.getShelter())
                .content(dto.getContent())
                .rating(dto.getRating())
                .build();

        reviewRepository.save(review);
    }

    public ReviewInfoResponse getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 리뷰입니다."));

        DateTimeFormatter format =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        ReviewInfoResponse info = ReviewInfoResponse.builder()
                .writer(review.getWriter().getNickname())
                .content(review.getContent())
                .rating(review.getRating())
                .registered(review.getRegistered().format(format))
                .shelter(review.getShelter())
                .build();

        return info;
    }

    public Page<ReviewAllInfoResponse> readAll(Integer pageNo, Long shelterId) {

        Pageable pageable = PageRequest.of(pageNo - 1, 20, Sort.Direction.DESC, "rating");
        Page<ReviewAllInfoResponse> review = reviewRepository.findAllByShelter(shelterId, pageable).map(ReviewAllInfoResponse::dto);

        return review;
    }


    @Transactional
    public void edit(String userId, Long reviewId, ReviewRequest dto) {
        userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new FindException("존재하지 않는 리뷰입니다."));

        review.setContent(dto.getContent());
        review.setShelter(dto.getShelter());
        review.setRating(dto.getRating());

        reviewRepository.save(review);
    }

    @Transactional
    public void delete(String userId, Long reviewId) {
        userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        reviewRepository.findById(reviewId)
                .orElseThrow(() -> new FindException("존재하지 않는 게시물입니다."));

        reviewRepository.deleteById(reviewId);
    }


}
