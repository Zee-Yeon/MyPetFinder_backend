package com.finder.mypet.board.service;

import com.finder.mypet.board.domain.entity.Board;
import com.finder.mypet.board.domain.entity.Category;
import com.finder.mypet.board.domain.repository.BoardRepository;
import com.finder.mypet.board.dto.request.BoardRequest;
import com.finder.mypet.board.dto.response.BoardAllInfoResponse;
import com.finder.mypet.board.dto.response.BoardInfoResponse;
import com.finder.mypet.comment.domain.repository.CommentRepository;
import com.finder.mypet.comment.dto.response.CommentResponse;
import com.finder.mypet.user.domain.entity.User;
import com.finder.mypet.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.FindException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
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

        DateTimeFormatter format =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        BoardInfoResponse info = BoardInfoResponse.dto(board);

        return info;
    }

    @Transactional(readOnly = true)
    public Page<BoardAllInfoResponse> readAll(Integer pageNo) {

        Pageable pageable = PageRequest.of(pageNo-1, 10, Sort.Direction.DESC, "registered");
        Page<BoardAllInfoResponse> board = boardRepository.findAll(pageable).map(BoardAllInfoResponse::dto);

        return board;
    }

    @Transactional(readOnly = true)
    public Page<BoardAllInfoResponse> readCategory(Category category, Integer pageNo, String order, String keyword) {

        order = (order == null) ? "registered" : "view";

        Pageable pageable = PageRequest.of(pageNo-1, 10, Sort.Direction.DESC, order);

        Page<BoardAllInfoResponse> board;

        if (keyword == null) {
            board = (category == null)
                    ? boardRepository.findAll(pageable).map(BoardAllInfoResponse::dto)
                    : boardRepository.findAllByCategory(category, pageable).map(BoardAllInfoResponse::dto);
        } else {
            board = boardRepository.findAllByTitleContaining(keyword, pageable).map(BoardAllInfoResponse::dto);
        }

        return board;
    }
//
//    @Transactional(readOnly = true)
//    public Page<BoardAllInfoResponse> searchKeyword(String search, Integer pageNo) {
//
//        Pageable pageable = PageRequest.of(pageNo-1, 10, Sort.Direction.DESC, "registered");
//        Page<BoardAllInfoResponse> board = boardRepository.findAllByTitleContaining(search, pageable).map(BoardAllInfoResponse::dto);
//
//        return board;
//    }

    @Transactional
    public void edit(String userId, Long boardId, BoardRequest dto) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new FindException("존재하지 않는 게시물입니다."));

        if (user.equals(board.getWriter())) {
            board.setCategory(dto.getCategory());
            board.setTitle(dto.getTitle());
            board.setContent(dto.getContent());

            boardRepository.save(board);
        }
    }

    @Transactional
    public void delete(String userId, Long boardId) {
        userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        boardRepository.findById(boardId)
                        .orElseThrow(() -> new FindException("존재하지 않는 게시물입니다."));

        boardRepository.deleteById(boardId);
    }

}
