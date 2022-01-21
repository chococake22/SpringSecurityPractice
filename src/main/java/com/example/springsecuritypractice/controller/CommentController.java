package com.example.springsecuritypractice.controller;

import com.example.springsecuritypractice.account.UserAccount;
import com.example.springsecuritypractice.board.Board;
import com.example.springsecuritypractice.board.BoardRepository;
import com.example.springsecuritypractice.board.BoardService;
import com.example.springsecuritypractice.comment.Comment;
import com.example.springsecuritypractice.comment.CommentRepository;
import com.example.springsecuritypractice.dto.BoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;


    @PostMapping("/commentMake/{id}")
    public String commentMake(Comment comment, @PathVariable Long id, Model model, @AuthenticationPrincipal UserAccount userAccount) {

        Comment comment1 = new Comment();

        System.out.println("여기는?");

        comment1.setWriter(userAccount.getUsername());
        comment1.setContent(comment.getContent());

        commentRepository.save(comment1);

        List<Comment> comments = commentRepository.findCommentsById(id);

        BoardDto board = boardService.boardDetail(id);

        model.addAttribute("userAccount", userAccount);
        model.addAttribute("board", board);
        model.addAttribute("comments", comments);

        return "redirect:/boardDetail/{id}";

    }
}
