package com.finder.mypet.board.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.finder.mypet.board.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor
@Getter
public class BoardRequest {
    private String title;
    private String content;
    private Category category;
}
