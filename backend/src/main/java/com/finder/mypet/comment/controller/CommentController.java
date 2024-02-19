package com.finder.mypet.comment.controller;

import com.finder.mypet.comment.dto.request.CommentSaveRequest;
import com.finder.mypet.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 쓰기
    @Transactional
    @PostMapping("/user/comment/{boardId}")
    public ResponseEntity<?> save(@AuthenticationPrincipal User user, @PathVariable("boradId") Long boardId, @RequestBody CommentSaveRequest dto) {
        String userId = user.getUsername();

        commentService.save(userId, boardId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
