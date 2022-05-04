package com.mire.nysjangtuh.validator;

import com.mire.nysjangtuh.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class BoardValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {

        Board board = (Board) obj;


        if(StringUtils.isEmpty(board.getContent())) {
            System.out.println("validate() if");
            errors.rejectValue("content", "key","내용을 입력해주세요.");
        }

    }
}
