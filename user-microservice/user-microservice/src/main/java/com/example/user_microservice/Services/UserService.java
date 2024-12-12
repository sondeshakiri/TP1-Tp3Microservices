package com.example.user_microservice.Services;



import java.util.List;
import java.util.Optional;

import com.example.user_microservice.entities.User;

public interface UserService {
    User saveUser(User user);
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    void deleteUser(Long id);
}
