package com.example.springsecuritypractice.validator;


import com.example.springsecuritypractice.board.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

// 제목 글자 수 제한과 같은 폼 양식 관련 제한에 관해서 세부적으로 정할 수 있다
@Component
public class BoardValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Board b = (Board) obj;
        if(StringUtils.isEmpty(b.getContent())) {   // 만약 내용(content)에 아무 것도 입력하지 않고 비어있다면
            errors.rejectValue("content", "key", "내용을 입력하세요");
        }
    }
}
