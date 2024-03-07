package com.orm.testing.controllers;

import com.orm.testing.data_access.IUserRepository;
import com.orm.testing.mappers.UserProfile;
import com.orm.testing.models.User;

import java.sql.SQLException;
import java.util.List;

public class UserController {
    private final IUserRepository userRepository;
    private final UserProfile userProfile;

    public UserController(IUserRepository userRepository, UserProfile userProfile) {
        this.userRepository = userRepository;
        this.userProfile = userProfile;
    }

    public List<User> getUsersWithCompletedTasks() throws SQLException {
        return userRepository.getAllWithTaskStatus("COMPLETED");
    }
}
