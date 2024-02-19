package com.finder.mypet.comment.service;

import com.finder.mypet.board.domain.entity.Board;
import com.finder.mypet.board.domain.repository.BoardRepository;
import com.finder.mypet.comment.domain.entity.Comment;
import com.finder.mypet.comment.domain.repository.CommentRepository;
import com.finder.mypet.comment.dto.request.CommentSaveRequest;
import com.finder.mypet.user.domain.entity.User;
import com.finder.mypet.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public void save(String userId, Long boardId, CommentSaveRequest dto) {

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
}
