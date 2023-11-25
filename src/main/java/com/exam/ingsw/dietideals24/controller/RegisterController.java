package com.exam.ingsw.dietideals24.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.exam.ingsw.dietideals24.model.User;
import com.exam.ingsw.dietideals24.service.IUserService;

@RestController
public class RegisterController {
    @Autowired
    @Qualifier("UserService")
    private IUserService userService;

    @PostMapping("/userSignUp")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
}