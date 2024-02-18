package com.finder.mypet.comment.service;

import com.finder.mypet.comment.domain.entity.Comment;
import com.finder.mypet.comment.domain.repository.CommentRepository;
import com.finder.mypet.user.domain.entity.User;
import com.finder.mypet.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

//    public void save(String userId, Long boardId, String content) {
//
//        Optional<User> writer = userRepository.findByUserId(userId);
//
//        Comment comment = Comment.builder()
//                .writer(writer)
//
//    }
}
