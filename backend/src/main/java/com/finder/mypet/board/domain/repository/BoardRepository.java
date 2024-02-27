package com.finder.mypet.board.domain.repository;


import com.finder.mypet.board.domain.entity.Board;
import com.finder.mypet.board.domain.entity.Category;
import com.finder.mypet.board.dto.response.BoardInfoResponse;
import com.finder.mypet.user.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAll(Pageable pageable);
    Page<Board> findAllByCategory(Category category, Pageable pageable);
    Page<Board> findByWriter(String writer);
    Page<Board> findAllByTitleContaining(String keyword, Pageable pageable);
}
