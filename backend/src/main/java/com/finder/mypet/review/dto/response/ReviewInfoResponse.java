package com.finder.mypet.review.dto.response;

import com.finder.mypet.review.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewInfoResponse {
    private String writer;
    private String content;
    private String registered;
    private int rating;
    private Long shelter;

    public static ReviewInfoResponse dto(Review review) {
        DateTimeFormatter format =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return ReviewInfoResponse.builder()
                .writer(review.getWriter().getNickname())
                .content(review.getContent())
                .rating(review.getRating())
                .registered(review.getRegistered().format(format))
                .shelter(review.getShelter())
                .build();
    }
}
