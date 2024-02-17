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
    private User writer;
    private List<Comment> commentList;

//    public BoardInfoResponse(Board board) {
//        this.category = board.getCategory();
//        this.title = board.getTitle();
//        this.content = board.getContent();
//        this.registered = board.getRegistered();
//        this.view = board.getView();
//        this.writer = board.getWriter();
//        this.commentList = board.getCommentList().stream().map;
//    }
}
