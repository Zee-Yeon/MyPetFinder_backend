package com.finder.mypet.comment.controller;

import com.finder.mypet.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

//    @PostMapping("/user/comment/{boradId}")
//    public ResponseEntity<?> save(@AuthenticationPrincipal User user, Long boardId, String content) {
//        String userId = user.getUsername();
//
//        commentService.save(userId, boardId, content);
//
//        return new ResponseEntity<>;
//    }
}
