package com.example.springsecuritypractice.controller;

import com.example.springsecuritypractice.board.Board;
import com.example.springsecuritypractice.board.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/boardWriteForm")
    public String boardForm(Model model) {

        return "/board/boardWriteForm";
    }

}
