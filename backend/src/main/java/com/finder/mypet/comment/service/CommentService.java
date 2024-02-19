package com.finder.mypet.comment.service;

import com.finder.mypet.board.domain.entity.Board;
import com.finder.mypet.board.domain.repository.BoardRepository;
import com.finder.mypet.comment.domain.entity.Comment;
import com.finder.mypet.comment.domain.repository.CommentRepository;
import com.finder.mypet.comment.dto.request.CommentRequest;
import com.finder.mypet.user.domain.entity.User;
import com.finder.mypet.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void save(String userId, Long boardId, CommentRequest dto) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 게시물입니다."));

        Comment comment = Comment.builder()
                .writer(user)
                .board(board)
                .content(dto.getContent())
                .build();

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(String userId, Long commentId, CommentRequest dto) {
        userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 댓글입니다."));

        if (dto.getContent() != null) comment.setContent(dto.getContent());

        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(String userId, Long commentId) {
        userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        commentRepository.findById(commentId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 댓글입니다."));

        commentRepository.deleteById(commentId);
    }
}

