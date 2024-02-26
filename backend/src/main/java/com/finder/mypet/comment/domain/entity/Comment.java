package com.finder.mypet.comment.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.finder.mypet.board.domain.entity.Board;
import com.finder.mypet.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OrderBy;
import org.springframework.core.annotation.Order;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @Builder.Default
    private LocalDateTime registered = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public void updateContent(String content) {
        this.content = content;
    }
}
