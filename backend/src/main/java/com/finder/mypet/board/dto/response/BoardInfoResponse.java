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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardInfoResponse {

    private Category category;
    private String title;
    private String content;
    private LocalDateTime registered;
    private int view;
    private String writer;
    private List<CommentDto> commentList = new ArrayList<>();

    @Getter
    public static class CommentDto {
        private String content;
        private String writer;
        private String createdTime;

        public CommentDto(Comment comment) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            this.content = comment.getContent();
            this.writer = comment.getWriter().getNickname();
            this.createdTime = comment.getRegistered().format(format);
        }
    }
}
