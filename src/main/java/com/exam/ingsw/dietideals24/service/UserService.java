package com.exam.ingsw.dietideals24.service;

import java.util.Optional;
import com.exam.ingsw.dietideals24.model.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.exam.ingsw.dietideals24.repository.IUserRepository;
import com.exam.ingsw.dietideals24.exception.UserNotFoundException;

@Service("UserService")
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    public UserService() {}

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User loginUser(User user) {
        Optional<User> userRetrieved = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (userRetrieved.isPresent()) {
            return userRetrieved.get();
        } else {
            throw new UserNotFoundException("ERROR: User not found for Login!");
        }
    }
}