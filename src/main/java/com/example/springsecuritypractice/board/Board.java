package com.example.springsecuritypractice.board;


import com.example.springsecuritypractice.comment.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @NotNull    // 비어있으면 안된다
    @Size(min = 2, max = 100)    // 2~50자리로 제목 글자 수 제한
    private String title;
    private String author;

    @NotNull
    @Length(min = 2, max = 3000)
    private String content;

    // 조회 수
    private int count;

    @Builder
    public Board(Long id, String title, String author, String content, int count) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.count = count;
    }
}
