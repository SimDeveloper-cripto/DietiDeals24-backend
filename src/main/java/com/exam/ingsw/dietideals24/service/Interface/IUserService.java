package com.exam.ingsw.dietideals24.service.Interface;

import com.exam.ingsw.dietideals24.model.User;
import com.exam.ingsw.dietideals24.model.helper.UserDTO;

public interface IUserService {
    User createUser(User user);
    User loginUser(User user);
    UserDTO retrieveUser(Integer userId, String email);
    void updateUser(UserDTO userDTO);
}