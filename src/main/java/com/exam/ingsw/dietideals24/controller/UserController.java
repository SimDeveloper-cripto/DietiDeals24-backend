package com.exam.ingsw.dietideals24.controller;

import com.exam.ingsw.dietideals24.model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.exam.ingsw.dietideals24.service.Interface.IUserService;
import com.exam.ingsw.dietideals24.exception.EmptyParametersException;

@RestController
public class UserController {
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

    @PostMapping("/userSignUp")
    public User createUser(@RequestBody User user) throws EmptyParametersException {
        String name     = user.getName();
        String surname  = user.getSurname();
        String email    = user.getEmail();
        String password = user.getPassword();

        if ((name.isEmpty() || name.isBlank()) || (surname.isEmpty() || surname.isBlank())
                || (email.isEmpty() || email.isBlank()) || (password.isEmpty() || password.isBlank())) {
            throw new EmptyParametersException("Registration Error: Encountered empty credentials!");
        } else {
            return userService.createUser(user);
        }
    }
}