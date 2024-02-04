package com.exam.ingsw.dietideals24.service.Interface;

import com.exam.ingsw.dietideals24.model.helper.UserDTO;

public interface IUserService {
    UserDTO registerUser(UserDTO userDTO);
    UserDTO loginUser(UserDTO user);
    UserDTO retrieveUser(Integer userId, String email);
    void updateUser(UserDTO userDTO);
}