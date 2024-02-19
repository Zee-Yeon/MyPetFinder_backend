package com.finder.mypet.comment.controller;

import com.finder.mypet.comment.dto.request.CommentRequest;
import com.finder.mypet.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 쓰기
    @PostMapping("/user/comment/{boardId}")
    public ResponseEntity<?> save(@AuthenticationPrincipal User user, @PathVariable("boardId") Long boardId, @RequestBody CommentRequest dto) {
        String userId = user.getUsername();

        commentService.save(userId, boardId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    // 댓글 수정
    @PutMapping("/user/comment/{commentId}")
    public ResponseEntity<?> updateComment(@AuthenticationPrincipal User user, @PathVariable("commentId") Long commentId, @RequestBody CommentRequest dto) {
        String userId = user.getUsername();

        commentService.updateComment(userId, commentId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/user/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@AuthenticationPrincipal User user, @PathVariable("commentId") Long commentId) {
        String userId = user.getUsername();

        commentService.deleteComment(userId, commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
