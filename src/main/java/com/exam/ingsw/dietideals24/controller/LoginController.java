package com.exam.ingsw.dietideals24.controller;

import com.exam.ingsw.dietideals24.model.User;
import com.exam.ingsw.dietideals24.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    @Qualifier("UserService")
    private IUserService userService;

    @GetMapping("/userLogin")
    public User findUser(@RequestParam String email, @RequestParam String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.loginUser(user);
    }
}