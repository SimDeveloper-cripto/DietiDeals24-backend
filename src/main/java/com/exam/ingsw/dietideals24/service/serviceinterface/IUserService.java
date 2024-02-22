package com.exam.ingsw.dietideals24.service.serviceinterface;

import com.exam.ingsw.dietideals24.model.dto.UserDTO;

public interface IUserService {
    UserDTO registerUser(UserDTO userDTO);
    UserDTO loginUser(UserDTO user);
    UserDTO retrieveUser(Integer userId, String email);
    void updateUser(UserDTO userDTO);
}