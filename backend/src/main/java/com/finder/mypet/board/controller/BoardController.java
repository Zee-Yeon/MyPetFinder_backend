package com.finder.mypet.board.controller;

import com.finder.mypet.board.domain.entity.Category;
import com.finder.mypet.board.dto.request.BoardRequest;
import com.finder.mypet.board.dto.response.BoardAllInfoResponse;
import com.finder.mypet.board.dto.response.BoardInfoResponse;
import com.finder.mypet.board.service.BoardService;
import com.finder.mypet.common.response.Response;
import com.finder.mypet.common.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import static com.finder.mypet.common.response.ResponseCode.*;


@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    // 게시글 작성 [ㅇ]
    @PostMapping("/user/board")
    public ResponseEntity<?> save(@AuthenticationPrincipal User user, @RequestBody BoardRequest dto) {
        String userId = user.getUsername();

        boardService.save(userId, dto);
        return new ResponseEntity<>(Response.create(CREATE_BOARD, null), CREATE_BOARD.getHttpStatus());
    }

    // 게시글 상세보기 (비회원도 가능) [ㅇ]
    @GetMapping("/user/board/{boardId}")
    public ResponseEntity<?> getBoard(@AuthenticationPrincipal User user, @PathVariable("boardId") Long boardId) {
        String userId = "";
        if (user != null) {
            userId = user.getUsername();
        }
        BoardInfoResponse board = boardService.getBoard(userId, boardId);
        return new ResponseEntity<>(Response.create(GET_BOARD, board), GET_BOARD.getHttpStatus());
    }
/*
    // 게시글 전체 조회 (커뮤니티 + Q&A)
    @GetMapping("/boards")
    public ResponseEntity<?> readAll(@RequestParam(required = false) Category category,
            @RequestParam(required = false, defaultValue = "1", value = "page") Integer pageNo) {
        if (pageNo == null) pageNo = 1;

        Page<BoardAllInfoResponse> boardList = boardService.readAll(pageNo);

        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

 */
    // 게시글 카테고리별 조회 [ㅇ]
    @GetMapping("/boards")
    public ResponseEntity<?> readCategory(@RequestParam(required = false) Category category,
                                          @RequestParam(required = false, defaultValue = "1", value = "page")Integer pageNo,
                                          @RequestParam(required = false, defaultValue = "registered") String order,
                                          @RequestParam(defaultValue = "") String keyword) {

        Page<BoardAllInfoResponse> boardList = boardService.readCategory(category, pageNo, order, keyword);

        return new ResponseEntity<>(Response.create(GET_BOARDS, boardList), GET_BOARDS.getHttpStatus());
    }
/*
    // 키워드 검색(제목만 가능)
    @GetMapping("/boards/search")
    public ResponseEntity<?> searchKeyword(@RequestParam("search") String search,
                                           @RequestParam(required = false, defaultValue = "1", value = "page")Integer pageNo) {
        Page<BoardAllInfoResponse> boardList = boardService.searchKeyword(search, pageNo);

        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }


 */
    // 수정 삭제 시, 해당 글 작성자가 일치하는지 확인 코드 빠졌음 !!

    // 게시글 수정 [ㅇ]
    @PutMapping("/user/board/{boardId}")
    public ResponseEntity<?> edit(@AuthenticationPrincipal User user, @PathVariable("boardId") Long boardId, @RequestBody BoardRequest dto){
        String userId = user.getUsername();

        boardService.edit(userId, boardId, dto);
        return new ResponseEntity<>(Response.create(EDIT_BOARDS, null), EDIT_BOARDS.getHttpStatus());
    }

    // 게시글 삭제 [ㅇ]
    @DeleteMapping("/user/board/{boardId}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user, @PathVariable("boardId") Long boardId){
        String userId = user.getUsername();
        boardService.delete(userId, boardId);
        return new ResponseEntity<>(Response.create(DELETE_BOARD, null), DELETE_BOARD.getHttpStatus());
    }
}
