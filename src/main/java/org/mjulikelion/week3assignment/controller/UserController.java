package org.mjulikelion.week3assignment.controller;

import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.domain.User;
import org.mjulikelion.week3assignment.sevice.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }
}
