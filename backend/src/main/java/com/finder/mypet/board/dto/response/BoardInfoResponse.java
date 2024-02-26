package com.finder.mypet.board.dto.response;

import com.finder.mypet.board.domain.entity.Board;
import com.finder.mypet.board.domain.entity.Category;
import com.finder.mypet.comment.domain.entity.Comment;
import com.finder.mypet.comment.dto.response.CommentResponse;
import com.finder.mypet.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardInfoResponse {

    private Long boardId;
    private Category category;
    private String title;
    private String content;
    private String registered;
    private int view;
    private String writer;
    private String filePath;
    private List<CommentResponse> commentList;

    public static BoardInfoResponse dto(Board board) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return BoardInfoResponse.builder()
                .boardId(board.getId())
                .category(board.getCategory())
                .title(board.getTitle())
                .content(board.getContent())
                .view(board.getView())
                .registered(board.getRegistered().format(format))
                .writer(board.getWriter().getNickname())
                .filePath(board.getFilePath())
                .commentList(board.getCommentList().stream().map(CommentResponse::dto).collect(Collectors.toList()))
                .build();
    }
}
