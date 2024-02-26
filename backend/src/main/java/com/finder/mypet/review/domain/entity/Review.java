package com.finder.mypet.review.domain.entity;

import com.finder.mypet.review.dto.request.ReviewRequest;
import com.finder.mypet.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Long shelter;

    private int rating;

    @Builder.Default
    private LocalDateTime registered = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    public void updateReview(ReviewRequest reviewRequest) {
        this.content = reviewRequest.getContent();
        this.rating = reviewRequest.getRating();
    }
}
