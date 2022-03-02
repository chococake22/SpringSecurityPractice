package com.example.springsecuritypractice.service;

import com.example.springsecuritypractice.comment.Comment;
import com.example.springsecuritypractice.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    CommentRepository commentRepository;

    @Transactional
    public List<Comment> getCommentList(@Param("id") Long id) {

        return commentRepository.findCommentsByBoard_Id(id);

    }
}
