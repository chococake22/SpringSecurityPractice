package com.example.springsecuritypractice.controller;

import com.example.springsecuritypractice.account.UserAccount;
import com.example.springsecuritypractice.board.Board;
import com.example.springsecuritypractice.board.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/list")
    public String list(Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards", boards);
        return "/board/boardList";
    }

    @GetMapping("/boardWrite")
    public String boardForm(Model model) {
        model.addAttribute("board", new Board());
        return "/board/boardWriteForm";
    }

    @PostMapping("/boardWrite")
    public String boardWriteCheck(@ModelAttribute Board board, @AuthenticationPrincipal UserAccount userAccount) {
        board.setAuthor(userAccount.getAccount().getUsername());
        boardRepository.save(board);
        return "redirect:/list";
    }

}
