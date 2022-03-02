package com.example.springsecuritypractice.service;

import com.example.springsecuritypractice.account.UserAccount;
import com.example.springsecuritypractice.board.Board;
import com.example.springsecuritypractice.dto.BoardDto;
import com.example.springsecuritypractice.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class BoardService {

    // 생성자 주입
    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Page<Board> getBoardList(Pageable pageable, @RequestParam(required = false, defaultValue = "") String search) {
                                                        // 매개변수가 필수는 아님, 기본값은 ""
        int page;

        if(pageable.getPageNumber() == 0) {
            page = 0;
        } else {
            page = pageable.getPageNumber() - 1;
        }

        // int page = pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1;

        System.out.println("pageable.getPageNumber() : " + pageable.getPageNumber());

        // id를 기준으로 내림차순 -> page에 10개씩 보여주기
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));

        // 검색어가 있을 경우 제목이나 내용에 해당 검색어가 있는 게시물만 가져온다.
        if(search != "") {
            return boardRepository.findByTitleContainingOrContentContaining(search, search, pageable);
        }

        // 검색어 없으면 전체 게시물 가져오기
        return boardRepository.findAll(pageable);

    }

    @Transactional
    public BoardDto boardDetail(Long id) {

        Board board = boardRepository.findById(id).get();

        if(board == null) {
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }

        BoardDto boardDto = BoardDto.builder()
                .id(board.getId())
                .author(board.getAuthor())
                .title(board.getTitle())
                .content(board.getContent())
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .build();

        return boardDto;
    }

    @Transactional
    public void update(Board board, UserAccount userAccount) {

        Board newBoard = boardRepository.findById(board.getId()).get();

        newBoard.setTitle(board.getTitle());
        newBoard.setContent(board.getContent());

    }
}
