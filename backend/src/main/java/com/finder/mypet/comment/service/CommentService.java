package com.finder.mypet.comment.service;

import com.finder.mypet.board.domain.entity.Board;
import com.finder.mypet.board.domain.repository.BoardRepository;
import com.finder.mypet.board.service.BoardService;
import com.finder.mypet.comment.domain.entity.Comment;
import com.finder.mypet.comment.domain.repository.CommentRepository;
import com.finder.mypet.comment.dto.request.CommentRequest;
import com.finder.mypet.common.advice.exception.CustomException;
import com.finder.mypet.common.response.ResponseCode;
import com.finder.mypet.review.domain.entity.Review;
import com.finder.mypet.user.domain.entity.User;
import com.finder.mypet.user.domain.repository.UserRepository;
import com.finder.mypet.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final BoardService boardService;

    @Transactional
    public void save(org.springframework.security.core.userdetails.User userDetail, Long boardId, CommentRequest commentRequest) {
        String userId = userService.userDetail(userDetail);
        User user = userService.findByUserId(userId);
        Board board = boardService.findByBoardId(boardId);

        Comment comment = Comment.builder()
                .writer(user)
                .board(board)
                .content(commentRequest.getContent())
                .build();

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(org.springframework.security.core.userdetails.User userDetail, Long commentId, CommentRequest commentRequest) {
        String userId = userService.userDetail(userDetail);
        User user = userService.findByUserId(userId);
        Comment comment = findByCommentId(commentId);

        checkWriter(user, comment);
        comment.updateContent(commentRequest.getContent());
    }

    @Transactional
    public void deleteComment(org.springframework.security.core.userdetails.User userDetail, Long commentId) {
        String userId = userService.userDetail(userDetail);
        User user = userService.findByUserId(userId);
        Comment comment = findByCommentId(commentId);

        checkWriter(user, comment);
        commentRepository.deleteById(commentId);
    }

    public Comment findByCommentId(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_COMMENT));
    }

    private void checkWriter(User user, Comment comment) {
        if (!user.getNickname().equals(comment.getWriter().getNickname())) {
            throw new CustomException(ResponseCode.NOT_AUTHORITY);
        }
    }
}

