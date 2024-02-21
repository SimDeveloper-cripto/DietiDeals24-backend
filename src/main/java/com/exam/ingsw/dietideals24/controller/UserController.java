package com.exam.ingsw.dietideals24.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.exam.ingsw.dietideals24.exception.EmptyParametersException;

import com.exam.ingsw.dietideals24.model.dto.UserDTO;
import com.exam.ingsw.dietideals24.service.Interface.IUserService;

@RestController
public class UserController {
    @Autowired
    @Qualifier("UserService")
    private IUserService userService;

    @PostMapping("/userLogin")
    public ResponseEntity<UserDTO> findUser(@RequestBody UserDTO userDTO) throws EmptyParametersException {
        if (userDTO == null)
            throw new EmptyParametersException("Login Error: DTO received is a NULL object!");
        else {
            return ResponseEntity.ok(userService.loginUser(userDTO));
        }
    }

    @PostMapping("/userSignUp")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) throws EmptyParametersException {
        String name     = userDTO.getName();
        String surname  = userDTO.getSurname();
        String email    = userDTO.getEmail();
        String password = userDTO.getPassword();

        if ((name.isEmpty() || name.isBlank()) || (surname.isEmpty() || surname.isBlank())
                || (email.isEmpty() || email.isBlank()) || (password.isEmpty() || password.isBlank())) {
            throw new EmptyParametersException("Registration Error: Encountered empty credentials!");
        } else {
            return ResponseEntity.ok(userService.registerUser(userDTO));
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