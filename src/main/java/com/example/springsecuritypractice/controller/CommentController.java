package com.example.springsecuritypractice.controller;

import com.example.springsecuritypractice.account.UserAccount;
import com.example.springsecuritypractice.board.Board;
import com.example.springsecuritypractice.board.BoardRepository;
import com.example.springsecuritypractice.board.BoardService;
import com.example.springsecuritypractice.comment.Comment;
import com.example.springsecuritypractice.comment.CommentRepository;
import com.example.springsecuritypractice.dto.BoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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
