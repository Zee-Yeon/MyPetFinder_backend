package com.finder.mypet.comment.dto.response;

import com.finder.mypet.comment.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private String content;
    private String writer;
    private String registered;

    public static CommentResponse dto(Comment comment) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return CommentResponse.builder()
                .content(comment.getContent())
                .writer(comment.getWriter().getNickname())
                .registered(comment.getRegistered().format(format))
                .build();
    }
}
