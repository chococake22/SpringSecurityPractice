package com.example.springsecuritypractice.repository;


import com.example.springsecuritypractice.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findById(Long id);

    List<Board> findByTitle(String title);

    List<Board> findByTitleOrContent(String title, String content);

    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    Page<Board> findByTitleContainingOrContentContainingOrderByIdDesc(String title, String content, Pageable pageable);

    // 조회수 증가
    @Modifying
    @Query("update Board b set b.count = b.count + 1 where b.id = :id")
    int updateCount(@Param("id") Long id);

}
