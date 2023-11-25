package com.exam.ingsw.dietideals24.controller;

import com.exam.ingsw.dietideals24.model.User;
import com.exam.ingsw.dietideals24.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    @Qualifier("UserService")
    private IUserService userService;

    @PostMapping("/userLogin")
    public User findUser(@RequestBody User user) {
        return userService.loginUser(user);
    }
}