package com.finder.mypet.comment.controller;

import com.finder.mypet.comment.dto.request.CommentRequest;
import com.finder.mypet.comment.service.CommentService;
import com.finder.mypet.common.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import static com.finder.mypet.common.response.ResponseCode.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 쓰기 [ㅇ]
    @PostMapping("/user/comment/{boardId}")
    public ResponseEntity<?> save(@AuthenticationPrincipal User user, @PathVariable("boardId") Long boardId, @RequestBody CommentRequest commentRequest) {
        commentService.save(user, boardId, commentRequest);
        return new ResponseEntity<>(Response.create(CREATE_COMMENT, null), CREATE_COMMENT.getHttpStatus());
    }
    // 댓글 수정 [ㅇ]
    @PutMapping("/user/comment/{commentId}")
    public ResponseEntity<?> updateComment(@AuthenticationPrincipal User user, @PathVariable("commentId") Long commentId, @RequestBody CommentRequest commentRequest) {
        commentService.updateComment(user, commentId, commentRequest);
        return new ResponseEntity<>(Response.create(EDIT_COMMENT, null), EDIT_COMMENT.getHttpStatus());
    }

    // 댓글 삭제 [ㅇ]
    @DeleteMapping("/user/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@AuthenticationPrincipal User user, @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(user, commentId);
        return new ResponseEntity<>(Response.create(DELETE_COMMENT, null), DELETE_COMMENT.getHttpStatus());
    }
}
