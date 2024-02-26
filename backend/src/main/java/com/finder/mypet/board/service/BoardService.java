package com.finder.mypet.board.service;

import com.finder.mypet.board.domain.entity.Board;
import com.finder.mypet.board.domain.entity.Category;
import com.finder.mypet.board.domain.repository.BoardRepository;
import com.finder.mypet.board.dto.request.BoardRequest;
import com.finder.mypet.board.dto.response.BoardAllInfoResponse;
import com.finder.mypet.board.dto.response.BoardInfoResponse;
import com.finder.mypet.common.advice.exception.CustomException;
import com.finder.mypet.common.response.ResponseCode;
import com.finder.mypet.user.domain.entity.User;
import com.finder.mypet.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;

    @Transactional
    public void save(org.springframework.security.core.userdetails.User userDetail, BoardRequest boardRequest) {
        User user = userService.userDetail(userDetail);
        checkCategory(boardRequest);

        Board board = Board.builder()
                .category(boardRequest.getCategory())
                .title(boardRequest.getTitle())
                .content(boardRequest.getContent())
                .writer(user)
                .build();

        boardRepository.save(board);
    }

    // 작성자가 조회할 때, 조회수는 올라가지 않음.
    @Transactional
    public BoardInfoResponse getBoard(org.springframework.security.core.userdetails.User userDetail, Long boardId) {
        String userId = "";
        if (userDetail != null) {
            userId = userDetail.getUsername();
        }
        Board board = findByBoardId(boardId);
        board.view(userId);
        return BoardInfoResponse.dto(board);
    }

    @Transactional(readOnly = true)
    public Page<BoardAllInfoResponse> readAll(Integer pageNo) {

        Pageable pageable = PageRequest.of(pageNo-1, 10, Sort.Direction.DESC, "registered");
        return boardRepository.findAll(pageable).map(BoardAllInfoResponse::dto);
    }

    @Transactional(readOnly = true)
    public Page<BoardAllInfoResponse> readCategory(Category category, Integer pageNo, String order, String keyword) {
        String orderBy = "registered";

        if (order.equals("view")) {
            orderBy = "view";
        }

        Pageable pageable = PageRequest.of(pageNo-1, 10, Sort.Direction.DESC, orderBy);

        Page<BoardAllInfoResponse> board;

        if (keyword.isEmpty()) {
            board = (category == null)
                    ? boardRepository.findAll(pageable).map(BoardAllInfoResponse::dto)
                    : boardRepository.findAllByCategory(category, pageable).map(BoardAllInfoResponse::dto);
        } else {
            board = boardRepository.findAllByTitleContaining(keyword, pageable).map(BoardAllInfoResponse::dto);
        }

        return board;
    }

//    @Transactional(readOnly = true)
//    public Page<BoardAllInfoResponse> searchKeyword(String search, Integer pageNo) {
//
//        Pageable pageable = PageRequest.of(pageNo-1, 10, Sort.Direction.DESC, "registered");
//        Page<BoardAllInfoResponse> board = boardRepository.findAllByTitleContaining(search, pageable).map(BoardAllInfoResponse::dto);
//
//        return board;
//    }
//    @Transactional(readOnly = true)
//    public Page<BoardAllInfoResponse> searchKeyword(String writer, Integer pageNo) {
//
//        Pageable pageable = PageRequest.of(pageNo-1, 10, Sort.Direction.DESC, "registered");
//        Page<BoardAllInfoResponse> board = boardRepository.findAllByWriterContaining(writer, pageable).map(BoardAllInfoResponse::dto);
//
//        return board;
//    }

    @Transactional
    public void edit(org.springframework.security.core.userdetails.User userDetail, Long boardId, BoardRequest boardRequest) {
        User user = userService.userDetail(userDetail);
        Board board = findByBoardId(boardId);

        checkWriter(user, board);
        checkCategory(boardRequest);

        board.update(boardRequest);
    }

    @Transactional
    public void delete(org.springframework.security.core.userdetails.User userDetail, Long boardId) {
        User user = userService.userDetail(userDetail);
        Board board = findByBoardId(boardId);

        checkWriter(user, board);
        boardRepository.deleteById(boardId);
    }

    private static void checkWriter(User user, Board board) {
        if (!user.getNickname().equals(board.getWriter().getNickname())) {
            throw new CustomException(ResponseCode.NOT_AUTHORITY);
        }
    }

    public Board findByBoardId(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_BOARD));
    }

    private static void checkCategory(BoardRequest dto) {
        if (!(dto.getCategory().equals(Category.QA) || dto.getCategory().equals(Category.COMMUNITY))) {
            throw new CustomException(ResponseCode.BAD_REQUEST);
        }
    }

}
