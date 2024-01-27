package com.exam.ingsw.dietideals24.controller;

import com.exam.ingsw.dietideals24.model.User;
import com.exam.ingsw.dietideals24.service.Interface.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;
import com.exam.ingsw.dietideals24.exception.EmptyParametersException;

@RestController
public class LoginController {
    @Autowired
    @Qualifier("UserService")
    private IUserService userService;

    @GetMapping("/userLogin")
    public User findUser(@RequestParam String email, @RequestParam String password) throws EmptyParametersException {
        User user = new User();

        if ((email.isEmpty() || email.isBlank()) || (password.isEmpty() || password.isBlank()))
            throw new EmptyParametersException("Login Error: At least one of email and password is an empty string!");
        else {
            user.setEmail(email);
            user.setPassword(password);
            return userService.loginUser(user);
        }
    }
}