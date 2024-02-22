package com.finder.mypet.board.dto.response;

import com.finder.mypet.board.domain.entity.Board;
import com.finder.mypet.board.domain.entity.Category;
import com.finder.mypet.comment.dto.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardAllInfoResponse {
    private Long boardId;
    private Category category;
    private String title;
    private String content;
    private String registered;
    private int view;
    private String writer;
    private int commentCount;

    public static BoardAllInfoResponse dto(Board board) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return BoardAllInfoResponse.builder()
                .boardId(board.getId())
                .category(board.getCategory())
                .title(board.getTitle())
                .content(board.getContent())
                .view(board.getView())
                .registered(board.getRegistered().format(format))
                .writer(board.getWriter().getNickname())
                .commentCount(board.getCommentList().size())
                .build();
    }
}
