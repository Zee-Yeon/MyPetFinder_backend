package com.finder.mypet.board.controller;

import com.finder.mypet.board.dto.request.BoardRequest;
import com.finder.mypet.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 작성
    @PostMapping("/user/board")
    public ResponseEntity<?> save(@AuthenticationPrincipal User user, @RequestBody BoardRequest dto) {
        String userId = user.getUsername();

        boardService.save(userId, dto.getCategory(), dto.getTitle(), dto.getContent());
        return new ResponseEntity<>(HttpStatus.OK);
    }

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
