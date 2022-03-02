package com.example.springsecuritypractice.repository;

import com.example.springsecuritypractice.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentsByBoard_Id(Long id);

    Comment deleteCommentByBoard_Id(Long id);
}
