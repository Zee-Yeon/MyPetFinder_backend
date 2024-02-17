package com.finder.mypet.comment.domain.repository;

import com.finder.mypet.comment.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
