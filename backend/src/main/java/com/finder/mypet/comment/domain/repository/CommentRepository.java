package com.finder.mypet.comment.domain.repository;

import com.finder.mypet.comment.domain.entity.Comment;
import com.finder.mypet.comment.dto.response.CommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByBoardId(Long boardId);
}
