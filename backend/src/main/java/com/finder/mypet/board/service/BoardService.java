package com.finder.mypet.board.service;

import com.finder.mypet.board.domain.entity.Board;
import com.finder.mypet.board.domain.repository.BoardRepository;
import com.finder.mypet.board.dto.request.BoardRequest;
import com.finder.mypet.board.dto.response.BoardInfoResponse;
import com.finder.mypet.comment.domain.repository.CommentRepository;
import com.finder.mypet.comment.dto.response.CommentResponse;
import com.finder.mypet.user.domain.entity.User;
import com.finder.mypet.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.FindException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public void save(String userId, BoardRequest dto) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        Board board = Board.builder()
                .category(dto.getCategory())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(user)
                .build();

        boardRepository.save(board);
    }

    // 작성자가 조회할 때, 조회수는 올라가지 않음.
    @Transactional
    public BoardInfoResponse getBoard(String userId, Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 게시물입니다."));

        User writer = board.getWriter();
        board.view(userId);


        BoardInfoResponse info = BoardInfoResponse.builder()
                .boardId(board.getId())
                .category(board.getCategory())
                .title(board.getTitle())
                .content(board.getContent())
                .view(board.getView())
                .registered(board.getRegistered())
                .writer(writer.getNickname())
                .commentList(board.getCommentList().stream().map(CommentResponse::dto).collect(Collectors.toList()))
                .build();

        return info;
    }

    @Transactional
    public void edit(String userId, Long boardId, BoardRequest dto) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new FindException("존재하지 않는 게시물입니다."));

        board.setCategory(dto.getCategory());
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());

        boardRepository.save(board);
    }

    public void delete(String userId, Long boardId) {
        userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        boardRepository.findById(boardId)
                        .orElseThrow(() -> new FindException("존재하지 않는 게시물입니다."));

        boardRepository.deleteById(boardId);
    }
}
