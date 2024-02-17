package com.finder.mypet.board.dto.response;

import com.finder.mypet.board.domain.entity.Board;
import com.finder.mypet.board.domain.entity.Category;
import com.finder.mypet.comment.domain.entity.Comment;
import com.finder.mypet.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardInfoResponse {

    private Category category;
    private String title;
    private String content;
    private LocalDateTime registered;
    private int view;
    private String writer;
    private List<Comment> commentList;


}
