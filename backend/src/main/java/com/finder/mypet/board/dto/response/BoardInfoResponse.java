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
    private List<CommentResponse> commentList;

}
