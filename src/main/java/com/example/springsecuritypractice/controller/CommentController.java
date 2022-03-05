package com.example.springsecuritypractice.controller;

import com.example.springsecuritypractice.account.UserAccount;
import com.example.springsecuritypractice.board.Board;
import com.example.springsecuritypractice.repository.BoardRepository;
import com.example.springsecuritypractice.service.BoardService;
import com.example.springsecuritypractice.comment.Comment;
import com.example.springsecuritypractice.repository.CommentRepository;
import com.example.springsecuritypractice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentService commentService;

    @Autowired
    BoardRepository boardRepository;

    BoardService boardService;



    // 댓글 가져오기
    @GetMapping("/commentList")
    public String commentList(@PathVariable Long id, Model model) {

//        List<Comment> comments = commentRepository.findCommentsByBoard_Id(id);

        List<Comment> comments = commentService.getCommentList(id);

        model.addAttribute("comments", comments);
        return "/board/boardDetail";
    }


    // 댓글 작성
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/commentCheck")
    public String commentCheck(Model model, Board board, Comment comment, HttpServletRequest request, @AuthenticationPrincipal UserAccount userAccount) {

        System.out.println("작성자 : " + comment.getWriter());
        System.out.println("내용 : " + comment.getContent());
        System.out.println("게시물 번호 : " + comment.getId());

        Comment comment1 = new Comment();
        comment1.setContent(comment.getContent());
        comment1.setWriter(comment.getWriter());
        comment1.setId(comment.getId());
        comment1.setBoard(board);

        commentRepository.save(comment1);

        model.addAttribute("com", comment.getContent());
        return "/board/boardDetail :: #comment_con";
    }


    @GetMapping("/commentDelete")
    public String deleteCheck(Comment comment, Board board) {

        System.out.println("댓글 번호: " + comment.getId());

        commentRepository.deleteById(comment.getId());
        return "/board/boardDetail";
    }
}
