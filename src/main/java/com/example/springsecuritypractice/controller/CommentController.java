package com.example.springsecuritypractice.controller;

import com.example.springsecuritypractice.repository.BoardRepository;
import com.example.springsecuritypractice.service.BoardService;
import com.example.springsecuritypractice.comment.Comment;
import com.example.springsecuritypractice.repository.CommentRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {


    CommentRepository commentRepository;

    BoardRepository boardRepository;

    BoardService boardService;

    // 댓글 가져오기
    @GetMapping("/commentList")
    public String commentList(@PathVariable Long id, Model model) {

        List<Comment> comments = commentRepository.findCommentsByBoard_Id(id);
        model.addAttribute("comments", comments);
        return "/board/boardDetail";
    }


    // 댓글 작성
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/commentCheck")
    public String commentCheck(Model model, HttpServletRequest request) {

        String con = request.getParameter("comment_content");
        System.out.println(con);

        Comment comment = new Comment();

        System.out.println("comment : " + comment.getContent());
        model.addAttribute("com", comment.getContent());
        return "/board/boardDetail :: #resultDiv";
    }


//    @GetMapping("/commentDelete/{id}")
//    public String deleteCheck(@PathVariable Long id, @Valid Comment comment) {
//        commentRepository.deleteById(id);
//        return "redirect:/list";
//    }
}
