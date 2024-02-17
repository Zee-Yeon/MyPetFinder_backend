package com.finder.mypet.board.service;

import com.finder.mypet.board.domain.entity.Board;
import com.finder.mypet.board.domain.entity.Category;
import com.finder.mypet.board.domain.repository.BoardRepository;
import com.finder.mypet.board.dto.request.BoardRequest;
import com.finder.mypet.board.dto.response.BoardInfoResponse;
import com.finder.mypet.user.domain.entity.User;
import com.finder.mypet.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public void save(String userId, Category category, String title, String content) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        Board board = Board.builder()
                .category(category)
                .title(title)
                .content(content)
                .writer(user)
                .build();

        boardRepository.save(board);
    }

    public BoardInfoResponse getBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 게시물입니다."));

        BoardInfoResponse info = BoardInfoResponse.builder()
                .category(board.getCategory())
                .title(board.getTitle())
                .content(board.getContent())
                .view(board.getView())
                .registered(board.getRegistered())
                .writer(board.getWriter())
                .commentList(board.getCommentList())
                .build();

        return info;
    }

    public void edit(String userId, Long boardId, BoardRequest dto) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        boardRepository.findById(boardId)
                .orElseThrow(() -> new FindException("존재하지 않는 게시물입니다."));

        delete(userId, boardId);

        Board board = Board.builder()
                .id(user.getId())
                .category(dto.getCategory())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(user)
                .build();

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
