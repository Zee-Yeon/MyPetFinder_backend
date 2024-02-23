package com.finder.mypet.comment.service;

import com.finder.mypet.board.domain.entity.Board;
import com.finder.mypet.board.domain.repository.BoardRepository;
import com.finder.mypet.comment.domain.entity.Comment;
import com.finder.mypet.comment.domain.repository.CommentRepository;
import com.finder.mypet.comment.dto.request.CommentRequest;
import com.finder.mypet.common.advice.exception.CustomException;
import com.finder.mypet.common.response.ResponseCode;
import com.finder.mypet.review.domain.entity.Review;
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
        User user = findByUserId(userId);
        Board board = findByBoardId(boardId);

        Comment comment = Comment.builder()
                .writer(user)
                .board(board)
                .content(dto.getContent())
                .build();

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(String userId, Long commentId, CommentRequest dto) {
        User user = findByUserId(userId);
        Comment comment = findByCommentId(commentId);

        if (!user.getNickname().equals(comment.getWriter().getNickname())) {
            throw new CustomException(ResponseCode.NOT_AUTHORITY);
        }
        if (dto.getContent() != null) comment.setContent(dto.getContent());

        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(String userId, Long commentId) {
        User user = findByUserId(userId);
        Comment comment = findByCommentId(commentId);

        if (!user.getNickname().equals(comment.getWriter().getNickname())) {
            throw new CustomException(ResponseCode.NOT_AUTHORITY);
        }

        commentRepository.deleteById(commentId);
    }

    public User findByUserId(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_USER));
        return user;
    }

    public Comment findByCommentId(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_COMMENT));
        return comment;
    }

    public Board findByBoardId(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_BOARD));
        return board;
    }
}

