package com.finder.mypet.board.domain.repository;


import com.finder.mypet.board.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
