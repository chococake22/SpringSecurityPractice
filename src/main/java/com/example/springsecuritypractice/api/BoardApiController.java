package com.example.springsecuritypractice.api;

import java.util.List;

import com.example.springsecuritypractice.board.Board;
import com.example.springsecuritypractice.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@RestController
@RequestMapping("/api")
class BoardApiController {

    @Autowired
    private BoardRepository repository;

    // 전체 게시물 가져오기
    @GetMapping("/boards")  // title이 전달될 경우 title로 검색, 아닐 경우 전체 데이터 가져오기
    List<Board> all(@RequestParam(required = false, defaultValue = "") String title,
                    @RequestParam(required = false) String content) {

        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {    // 제목, 내용이 모두 빈칸이면
            return repository.findAll();    // 전체 데이터 가져오기
        } else {
            // 제목이나 내용에 맞는 것을 가져온다.
            return repository.findByTitleOrContent(title, content);
        }
    }

    // 새로운 글 저장
    @PostMapping(value = "/boards")
    Board newBoard(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }

    // Single item

    // 하나의 게시물 가져오기
    @GetMapping("/boards/{id}")
    Board one(@PathVariable @Param("id") Long id) {

        return repository.findById(id)
                .orElse(null);
    }

    // 게시글 수정
    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

        return repository.findById(id)
                .map(Board -> {
                    Board.setTitle(newBoard.getTitle());
                    Board.setContent(newBoard.getContent());
                    return repository.save(Board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }
}