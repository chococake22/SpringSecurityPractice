package com.example.springsecuritypractice.dto;

import com.example.springsecuritypractice.board.Board;

import javax.persistence.*;
import java.util.Objects;

public class CommentDto {

    private Long id;

    private String content;

    private String writer;

    private Board board;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
