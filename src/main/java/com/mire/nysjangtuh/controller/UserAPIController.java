package com.mire.nysjangtuh.controller;


import com.mire.nysjangtuh.model.Board;


import com.mire.nysjangtuh.model.QUser;
import com.mire.nysjangtuh.model.User;

import com.mire.nysjangtuh.repository.UserRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
class UserAPIController {

    @Autowired
    private UserRepository userRepository;

    UserAPIController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/users")
    Iterable<User> all(@RequestParam(required = false) String method,
                   @RequestParam(required = false) String text) {

//        List<User> users = null;
        Iterable<User> users = null;

        if("query".equals(method)){
            users = userRepository.findByUsernameQuery(text);

        } else if(method.equals("nativeQuery")){
            users = userRepository.findByUsernameNativeQuery(text);

        } else if("querydsl".equals(method)){

            QUser user = QUser.user;
            Predicate predicate = user.username.contains(text);

            users = userRepository.findAll(predicate);
        } else {
            users = userRepository.findAll();
        }


        return users;
    }
    // end::get-aggregate-root[]

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    // Single item

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {

        return userRepository.findById(id)
                .orElse(null);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return userRepository.findById(id)
                .map(user -> {
//                    user.setTitle(newUser.getTitle());
//                    user.setContent(newUser.getContent());
                    user.setBoards(newUser.getBoards());

                    for (Board board: user.getBoards()) {
                        board.setUser(user);
                    }

                    return userRepository.save(user);
                })
                .orElseGet(() -> {
//                    newUser.setNum(id);
                    return userRepository.save(newUser);
                });
    }


    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}