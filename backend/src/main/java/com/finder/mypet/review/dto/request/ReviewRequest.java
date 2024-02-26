package com.finder.mypet.review.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class ReviewRequest {
    private Long shelter;

    @NotNull
    private String content;

    @NotNull
    private int rating;
}
