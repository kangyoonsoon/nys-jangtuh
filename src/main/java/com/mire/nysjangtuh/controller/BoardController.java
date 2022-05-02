package com.mire.nysjangtuh.controller;


import com.mire.nysjangtuh.model.Board;
import com.mire.nysjangtuh.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/")
    public String list(Model model){

        List<Board> boards = boardRepository.findAll();
        System.out.println(boards);
        model.addAttribute("boards", boards);
        return "boards/board";
    }

    @GetMapping("/form")
    public String from(Model model) {
        return "boards/form";
    }

}