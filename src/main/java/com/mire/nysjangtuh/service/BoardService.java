package com.mire.nysjangtuh.service;

import com.mire.nysjangtuh.model.Board;
import com.mire.nysjangtuh.model.User;
import com.mire.nysjangtuh.repository.BoardRepository;
import com.mire.nysjangtuh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;


    public Page<Board> findByTitleOrContentContaining(String searchText1, String searchText2, Pageable pageable){


        Page<Board> boards = boardRepository.findByTitleOrContentContaining(searchText1, searchText2, pageable);

        return boards;
    }

    public void save(String username, Board board, MultipartFile file) throws IOException {

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        System.out.println("projectPath: " + projectPath);

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File savedFile = new File(projectPath, fileName);

        file.transferTo(savedFile);

        User user = userRepository.findByUsername(username);
        board.setUser(user);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);

    }



}
