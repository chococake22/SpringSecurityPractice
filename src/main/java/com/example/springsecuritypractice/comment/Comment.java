package com.example.springsecuritypractice.comment;

import com.example.springsecuritypractice.board.Board;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Comment {

    @Id @GeneratedValue
    private Long id;

    private String content;

    private String writer;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(getId(), comment.getId()) && Objects.equals(getContent(), comment.getContent()) && Objects.equals(getWriter(), comment.getWriter()) && Objects.equals(getBoard(), comment.getBoard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getContent(), getWriter(), getBoard());
    }
}
