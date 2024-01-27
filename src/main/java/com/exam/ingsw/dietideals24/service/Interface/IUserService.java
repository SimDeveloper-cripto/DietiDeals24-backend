package com.exam.ingsw.dietideals24.service.Interface;

import com.exam.ingsw.dietideals24.model.User;

public interface IUserService {
    User createUser(User user);
    User loginUser(User user);
}