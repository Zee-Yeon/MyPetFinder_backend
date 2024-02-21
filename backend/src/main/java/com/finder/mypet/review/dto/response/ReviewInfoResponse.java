package com.finder.mypet.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


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
}
