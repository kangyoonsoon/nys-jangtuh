package com.mire.nysjangtuh.controller;


import com.mire.nysjangtuh.model.Board;
import com.mire.nysjangtuh.model.User;
import com.mire.nysjangtuh.repository.BoardRepository;
import com.mire.nysjangtuh.service.BoardService;
import com.mire.nysjangtuh.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model,
                       @PageableDefault(page = 0, size = 10, sort = "num", direction = Sort.Direction.DESC ) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText){


        //Page<Board> boards = boardRepository.findAll(pageable);
        //Page<Board> boards = boardRepository.findByTitleOrContentContaining(searchText, searchText, pageable);
        Page<Board> boards = boardService.findByTitleOrContentContaining(searchText, searchText, pageable);



        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 5);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
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
    public String formSubmit(@Valid Board board,
                             MultipartFile file,
                             Authentication authentication,
                             BindingResult bindingResult) throws IOException {

        boardValidator.validate(board, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println("inside bindingResult.hasErrors()");
            return "boards/form";
        }
        String username = authentication.getName();

        //boardRepository.save(board);
        boardService.save(username, board, file);
        return "redirect:/board/list";
    }

}