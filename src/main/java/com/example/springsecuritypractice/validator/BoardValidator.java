package com.example.springsecuritypractice.validator;


import com.example.springsecuritypractice.board.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

// 제목 글자 수 제한과 같은 폼 양식 관련 제한에 관해서 세부적으로 정할 수 있다
// Validator는 객체를 검증할 수 있는 기능 제공
@Component
public class BoardValidator implements Validator {

    // 객체를 검증할 경우 Validator가 검증할 수 있는 클래스의 객체인지 판단해주는 메서드
    // 파라미터로 들어온 객체가 Board 클래스인가 아닌가 판단
    // Board 객체를 판단할 것이기 때문에 Board.class를 반환한다
    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz);
    }

    // 검증을 위한 로직을 구현하는 메서드
    @Override
    public void validate(Object obj, Errors errors) {
        Board b = (Board) obj;
        if(StringUtils.isEmpty(b.getContent())) {   // 만약 내용(content)에 아무 것도 입력하지 않고 비어있다면
            errors.rejectValue("content", "key", "반드시 내용을 입력해야 합니다.");
        }

        if(StringUtils.isEmpty(b.getTitle())) {
            errors.rejectValue("title", "notitle", "제목에 내용이 없습니다.");
        }

    }
}
