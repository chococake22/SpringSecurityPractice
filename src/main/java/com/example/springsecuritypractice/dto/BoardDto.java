package com.example.springsecuritypractice.dto;

import com.example.springsecuritypractice.board.BaseTimeEntity;
import com.example.springsecuritypractice.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardDto {

    private Long id;
    private String title;
    private String author;
    private String content;
    private int count;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Board toEntity() {
        Board build = Board.builder()
                .id(id)
                .author(author)
                .title(title)
                .content(content)
                .count(count)
                .build();
        return build;
    }

    @Builder

    public BoardDto(Long id, String title, String author, String content, int count, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.count = count;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
