package com.example.springsecuritypractice.controller;

import com.example.springsecuritypractice.account.UserAccount;
import com.example.springsecuritypractice.board.Board;
import com.example.springsecuritypractice.board.BoardRepository;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 10) Pageable pageable,
                       // 검색어가 없으면 리스트 전체를 가져온다(검색어가 있어도 되고 없어도 된다)
                       @RequestParam(required = false, defaultValue = "") String search) {
        // Page<Board> boards = boardRepository.findAll(pageable);    // 첫 페이지가 0

        // 검색을 통해서 가져오기 (제목이나 내용에 해당 검색어가 있으면 가져온다)
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContainingOrderByIdDesc(search, search, pageable);

        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 5);  // 페이지 번호가 1보다 작은 값이 나오지 않도록
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
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

}
