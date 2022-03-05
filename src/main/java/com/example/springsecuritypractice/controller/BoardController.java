package com.example.springsecuritypractice.controller;

import com.example.springsecuritypractice.account.UserAccount;
import com.example.springsecuritypractice.board.Board;
import com.example.springsecuritypractice.repository.BoardRepository;
import com.example.springsecuritypractice.service.BoardService;
import com.example.springsecuritypractice.comment.Comment;
import com.example.springsecuritypractice.repository.CommentRepository;
import com.example.springsecuritypractice.dto.BoardDto;
import com.example.springsecuritypractice.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardValidator boardValidator;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/list")
    public String list(Model model, Pageable pageable,
                       // 검색어가 없으면 리스트 전체를 가져온다(검색어가 있어도 되고 없어도 된다)
                       @RequestParam(required = false, defaultValue = "") String search, @AuthenticationPrincipal UserAccount userAccount) {

        // @RequestParam은 기본적으로 아무 것도 입력하지 않으므로 defaultValue가 ""이다.
        // 하지만 boardList의 검색에서 검색어를 입력하 경우 value값이 생성된다.
        // 만약에 검색어가 있을 경우 param.value => search에 담기게 된다

        // Page<Board> boards = boardRepository.findAll(pageable);    // 첫 페이지가 0

        // 검색을 통해서 가져오기 (제목이나 내용에 해당 검색어가 있으면 가져온다)
        // Page<Board> boards = boardRepository.findByTitleContainingOrContentContainingOrderByIdDesc(search, search pageable);
        // 하지만 위 코드를 사용할 경우 오류가 발생함(1페이지를 누르면 오류가 난다)

        Page<Board> boards = boardService.getBoardList(pageable, search);
        model.addAttribute("boards", boards);
        model.addAttribute("userAccount", userAccount);
        return "board/boardList";
    }

    // 게시물 작성
    @GetMapping("/boardWrite")          // 새글 작성시 id가 필요 없기 때문에 required는 false(파라미터가 꼭 필요하지는 않다)
    public String boardForm(Model model, @RequestParam(required = false) Long id, @AuthenticationPrincipal UserAccount userAccount) {

        if(id == null) {
            model.addAttribute("board", new Board());
        } else {
            Board board = boardRepository.findById(id).orElse(null);    // 조회된 id가 없을 경우 null을 반환한다.
            model.addAttribute("board", board);
        }

        model.addAttribute("userAccount", userAccount);

        return "board/boardWriteForm";
    }

    // 게시물 작성 확인
    @PostMapping("/boardWrite")
    public String boardWriteCheck(@Valid Board board, BindingResult bindingResult, @AuthenticationPrincipal UserAccount userAccount, Model model) {

        boardValidator.validate(board, bindingResult);

        if(bindingResult.hasErrors()) {
            return "board/boardWriteForm";
        }

        board.setAuthor(userAccount.getAccount().getUsername());
        boardRepository.save(board);

        model.addAttribute("userAccount", userAccount);

        return "redirect:/list";
    }

    // 게시물 상세보기
    @GetMapping("/boardDetail/{id}")
    public String boardDetail(@PathVariable Long id,  Model model, @AuthenticationPrincipal UserAccount userAccount) {

        BoardDto board = boardService.boardDetail(id);
        boardRepository.updateCount(id);
        Object nlString = System.getProperty("line.separator").toString(); // content에서 줄바꿈 적용을 위해 사용되는 객체

        System.out.println("id : " + id);

        List<Comment> comments = commentRepository.findCommentsByBoard_Id(id);


        model.addAttribute("board", board);
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("nlString", nlString);
        model.addAttribute("comments", comments);

        return "board/boardDetail";
    }

    // 게시물 수정
    @GetMapping("/boardUpdate/{id}")
    public String updateForm(@PathVariable Long id, @AuthenticationPrincipal UserAccount userAccount, Model model) {

        BoardDto board = boardService.boardDetail(id);

        model.addAttribute("board", board);
        model.addAttribute("userAccount", userAccount);

        return "/board/boardUpdateForm";
    }

    // 게시물 수정 확인
    @PostMapping("/boardUpdate/{id}")
    public String updateCheck(@PathVariable Long id, @Valid Board board, @AuthenticationPrincipal UserAccount userAccount, Model model) {

       boardService.update(board, userAccount);
       model.addAttribute("userAccount", userAccount);

        return "redirect:/";

    }

    // 게시물 삭제
    @GetMapping("/boardDelete/{id}")
    public String deleteCheck(@PathVariable Long id) {
        boardRepository.deleteById(id);
        return "redirect:/list";
    }






}
