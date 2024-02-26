package com.finder.mypet.board.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.finder.mypet.board.dto.request.BoardRequest;
import com.finder.mypet.comment.domain.entity.Comment;
import com.finder.mypet.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OrderBy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 10000)
    private String content;

    // 조회수
    private int view;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder.Default
    private LocalDateTime registered = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    @OneToMany(mappedBy = "board")
    @OrderBy(clause = "registered DESC")
    private List<Comment> commentList = new ArrayList<>();

    // 해당 게시글을 조회한다는 행위의 메서드
    public void view(String userId) {
        if (!userId.equals(writer.getUserId())) {
            view++;
        }
    }

    public void update(BoardRequest boardRequest) {
        this.category = boardRequest.getCategory();
        this.title = boardRequest.getTitle();
        this.content = boardRequest.getContent();
    }
}
