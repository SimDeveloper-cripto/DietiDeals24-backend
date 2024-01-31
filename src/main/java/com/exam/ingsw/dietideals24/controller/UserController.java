package com.exam.ingsw.dietideals24.controller;

import com.exam.ingsw.dietideals24.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.exam.ingsw.dietideals24.model.helper.UserDTO;
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

    @GetMapping("/user/findUser")
    public ResponseEntity<UserDTO> retrieveUser(@RequestParam Integer userId, @RequestParam String email) throws EmptyParametersException {
        if (userId == null || email == null || email.isEmpty()) throw new EmptyParametersException("retrieveUser() --> at least of parameter is NULL");
        else {
            UserDTO userDTO = userService.retrieveUser(userId, email);
            return ResponseEntity.ok(userDTO);
        }
    }

    @PostMapping("/user/updateUser")
    public void update(@RequestBody UserDTO userDTO) throws EmptyParametersException {
        if (userDTO == null) throw new EmptyParametersException("User to be updated is NULL!");
        else userService.updateUser(userDTO);
    }
}