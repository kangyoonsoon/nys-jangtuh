package com.mire.nysjangtuh.controller;


import com.mire.nysjangtuh.model.Board;
import com.mire.nysjangtuh.repository.BoardRepository;
import com.mire.nysjangtuh.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/")
    public String list(Model model){

        List<Board> boards = boardRepository.findAll();
//        System.out.println(boards);
        model.addAttribute("boards", boards);
        return "boards/board";
    }

    @GetMapping("/form")
    public String getForm(Model model, @RequestParam(required = false) Long num) {
        if (num == null) {
            model.addAttribute("board", new Board());
        } else {

            Board board = boardRepository.getById(num);

            model.addAttribute("board", board);
        }

        return "boards/form";
    }

    @PostMapping("/form")
    public String formSubmit(@Valid Board board, BindingResult bindingResult) {

        boardValidator.validate(board, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println("inside bindingResult.hasErrors()");
            return "boards/form";
        }

        boardRepository.save(board);
        return "redirect:/board/";
    }

}