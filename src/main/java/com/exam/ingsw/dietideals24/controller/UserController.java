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

    @PostMapping("/userLogin")
    public UserDTO findUser(@RequestBody UserDTO userDTO) throws EmptyParametersException {
        User user = new User();

        if (userDTO == null)
            throw new EmptyParametersException("Login Error: DTO received is a NULL object!");
        else {
            return userService.loginUser(userDTO);
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