package com.mire.nysjangtuh.service;

import com.mire.nysjangtuh.model.Board;
import com.mire.nysjangtuh.model.User;
import com.mire.nysjangtuh.repository.BoardRepository;
import com.mire.nysjangtuh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public Board save(String username, Board board) {

        User user = userRepository.findByUsername(username);
        board.setUser(user);
        boardRepository.save(board);
        return null;
    }
}
