package com.exam.ingsw.dietideals24.service;

import java.util.Optional;
import com.exam.ingsw.dietideals24.model.User;
import org.springframework.stereotype.Service;
import com.exam.ingsw.dietideals24.model.helper.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.exam.ingsw.dietideals24.repository.IUserRepository;
import com.exam.ingsw.dietideals24.service.Interface.IUserService;
import com.exam.ingsw.dietideals24.exception.UserNotFoundException;

@Service("UserService")
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

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

    @Override
    public UserDTO retrieveUser(Integer userId, String email) {
        Optional<User> retrievedUser = userRepository.findByIdAndEmail(userId, email);

        /* Useless to check if retrievedUser is present. At this point the user has already loggedIn. We know it exists. */
        User user = retrievedUser.get();

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setBio(user.getBio());
        userDTO.setWebSiteUrl(user.getWebSiteUrl());
        return userDTO;
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setBio(userDTO.getBio());
        user.setWebSiteUrl(userDTO.getWebSiteUrl());

        userRepository.save(user);
    }
}