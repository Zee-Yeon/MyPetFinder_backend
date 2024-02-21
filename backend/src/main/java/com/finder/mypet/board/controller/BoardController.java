package com.finder.mypet.board.controller;

import com.finder.mypet.board.dto.request.BoardRequest;
import com.finder.mypet.board.dto.response.BoardAllInfoResponse;
import com.finder.mypet.board.dto.response.BoardInfoResponse;
import com.finder.mypet.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    // 게시글 작성 (category , 다른 수 입력 -> 예외처리 하기)
    @PostMapping("/user/board")
    public ResponseEntity<?> save(@AuthenticationPrincipal User user, @RequestBody BoardRequest dto) {
        String userId = user.getUsername();

        boardService.save(userId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 게시글 상세보기 (비회원도 가능)
    @GetMapping("/user/board/{boardId}")
    public ResponseEntity<?> getBoard(@AuthenticationPrincipal User user, @PathVariable("boardId") Long id) {
        String userId = "";
        if (user != null) {
            userId = user.getUsername();
        }

        BoardInfoResponse board = boardService.getBoard(userId, id);

        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    // 게시글 전체 조회 (커뮤니티 + Q&A)
    @GetMapping("/boards")
    public ResponseEntity<?> readAll(@RequestParam(required = false, defaultValue = "1", value = "page") Integer pageNo) {
        if (pageNo == null) pageNo = 1;
        Page<BoardAllInfoResponse> boardList = boardService.readAll(pageNo);

        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

    // 수정 삭제 시, 해당 글 작성자가 일치하는지 확인 코드 빠졌음 !!

    // 게시글 수정
    @PutMapping("/user/board/{boardId}")
    public ResponseEntity<?> edit(@AuthenticationPrincipal User user, @PathVariable("boardId") Long boardId, @RequestBody BoardRequest dto){
        String userId = user.getUsername();

        boardService.edit(userId, boardId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/user/board/{boardId}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user, @PathVariable("boardId") Long boardId){
        String userId = user.getUsername();

        boardService.delete(userId, boardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
