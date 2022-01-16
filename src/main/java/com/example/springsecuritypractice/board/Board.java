package com.example.springsecuritypractice.board;




import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @NotNull    // 비어있으면 안된고
    @Size(min = 2, max = 50, message = "제목은 2자 이상을 입력해야 합니다.")    // 2~50자리로 제목 글자 수 제한
    private String title;
    private String author;

    @NotNull
    @Size(min = 3, max = 40, message = "3자 이상 40자 이하를 입력해야 한다요.")
    private String content;

    @Builder
    public Board(Long id, String title, String author, String content) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
    }
}
