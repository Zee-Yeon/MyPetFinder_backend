package com.finder.mypet.review.dto.response;

import com.finder.mypet.review.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewAllInfoResponse {
    private Long id;
    private String content;
    private Long shelter;
    private int rating;
    private String registered;
    private String writer;

    public static ReviewAllInfoResponse dto(Review review) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return ReviewAllInfoResponse.builder()
                .id(review.getId())
                .content(review.getContent())
                .shelter(review.getShelter())
                .rating(review.getRating())
                .registered(review.getRegistered().format(format))
                .writer(review.getWriter().getNickname())
                .build();
    }
}
