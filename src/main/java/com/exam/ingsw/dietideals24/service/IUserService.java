package com.exam.ingsw.dietideals24.service;

import com.exam.ingsw.dietideals24.model.User;

public interface IUserService {
    User createUser(User user);
    User loginUser(User user);
}