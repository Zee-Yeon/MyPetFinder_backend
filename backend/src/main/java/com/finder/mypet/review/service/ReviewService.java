package com.finder.mypet.review.service;

import com.finder.mypet.board.domain.entity.Board;
import com.finder.mypet.board.dto.request.BoardRequest;
import com.finder.mypet.review.domain.entity.Review;
import com.finder.mypet.review.domain.repository.ReviewRepository;
import com.finder.mypet.review.dto.request.ReviewRequest;
import com.finder.mypet.review.dto.response.ReviewInfoResponse;
import com.finder.mypet.user.domain.entity.User;
import com.finder.mypet.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

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

    public void delete(String userId, Long reviewId) {
        userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        reviewRepository.findById(reviewId)
                .orElseThrow(() -> new FindException("존재하지 않는 게시물입니다."));

        reviewRepository.deleteById(reviewId);
    }
}
