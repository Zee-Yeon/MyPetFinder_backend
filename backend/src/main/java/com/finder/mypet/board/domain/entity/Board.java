package com.finder.mypet.board.domain.entity;

import com.finder.mypet.comment.domain.entity.Comment;
import com.finder.mypet.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
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

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    // 조회수 (작성자가 조회할 때, 조회수는 올라가지 않음.)
    public int getView() {
        return view + 1;
    }
}
