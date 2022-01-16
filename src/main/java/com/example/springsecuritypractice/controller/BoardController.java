package com.example.springsecuritypractice.controller;

import com.example.springsecuritypractice.account.UserAccount;
import com.example.springsecuritypractice.board.Board;
import com.example.springsecuritypractice.board.BoardRepository;
import com.example.springsecuritypractice.board.BoardService;
import com.example.springsecuritypractice.dto.BoardDto;
import com.example.springsecuritypractice.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, Pageable pageable,
                       // 검색어가 없으면 리스트 전체를 가져온다(검색어가 있어도 되고 없어도 된다)
                       @RequestParam(required = false, defaultValue = "") String search) {

        // @RequestParam은 기본적으로 아무 것도 입력하지 않으므로 defaultValue가 ""이다.
        // 하지만 boardList의 검색에서 검색어를 입력하 경우 value값이 생성된다.
        // 만약에 검색어가 있을 경우 param.value => search에 담기게 된다

        // Page<Board> boards = boardRepository.findAll(pageable);    // 첫 페이지가 0

        // 검색을 통해서 가져오기 (제목이나 내용에 해당 검색어가 있으면 가져온다)
        // Page<Board> boards = boardRepository.findByTitleContainingOrContentContainingOrderByIdDesc(search, search pageable);
        // 하지만 위 코드를 사용할 경우 오류가 발생함(1페이지를 누르면 오류가 난다)

        Page<Board> boards = boardService.getBoardList(pageable, search);
        model.addAttribute("boards", boards);
        return "/board/boardList";
    }

    @GetMapping("/boardWrite")          // 새글 작성시 id가 필요 없기 때문에 required는 false
    public String boardForm(Model model, @RequestParam(required = false) Long id) {

        if(id == null) {
            model.addAttribute("board", new Board());
        } else {
            Board board = boardRepository.findById(id).orElse(null);    // 조회된 id가 없을 경우 null을 반환한다.
            model.addAttribute("board", board);
        }
        return "/board/boardWriteForm";
    }

    @PostMapping("/boardWrite")
    public String boardWriteCheck(@Valid Board board, BindingResult bindingResult, @AuthenticationPrincipal UserAccount userAccount) {

        boardValidator.validate(board, bindingResult);

        if(bindingResult.hasErrors()) {
            return "/board/boardWriteForm";
        }

        board.setAuthor(userAccount.getAccount().getUsername());
        boardRepository.save(board);
        return "redirect:/list";
    }

    @GetMapping("/boardDetail/{id}")
    public String boardDetail(@PathVariable Long id, Model model) {

        System.out.println("123123");

        BoardDto board = boardService.boardDetail(id);

        model.addAttribute("board", board);

        return "/board/boardDetail";
    }

}
