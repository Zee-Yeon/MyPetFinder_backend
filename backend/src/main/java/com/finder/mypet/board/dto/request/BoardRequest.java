package com.finder.mypet.board.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.finder.mypet.board.domain.entity.Category;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class BoardRequest {
    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private Category category;
}
