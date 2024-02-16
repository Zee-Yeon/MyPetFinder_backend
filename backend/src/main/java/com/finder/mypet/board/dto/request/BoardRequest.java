package com.finder.mypet.board.dto.request;

import com.finder.mypet.board.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class BoardRequest {
    private String title;
    private String content;
    private Category category;
}
