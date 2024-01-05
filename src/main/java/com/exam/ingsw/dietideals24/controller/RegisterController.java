package com.exam.ingsw.dietideals24.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.exam.ingsw.dietideals24.exception.EmptyParametersException;

import com.exam.ingsw.dietideals24.model.User;
import com.exam.ingsw.dietideals24.service.IUserService;

@RestController
public class RegisterController {
    @Autowired
    @Qualifier("UserService")
    private IUserService userService;

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