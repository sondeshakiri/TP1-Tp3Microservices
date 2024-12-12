package com.example.user_microservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.user_microservice.Services.UserService;
import com.example.user_microservice.entities.User;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    public User fallback(Long userId, Exception e) {
        // Retourner un utilisateur générique en cas de défaillance
        return new User(userId, "Utilisateur non disponible pour le moment. Veuillez réessayer plus tard.");
    }

    @GetMapping("/userDetails")
    @Retry(name = "myRetry", fallbackMethod = "fallback")
    @RateLimiter(name = "myRateLimiter", fallbackMethod = "fallback")
    @CircuitBreaker(name = "usermicroService", fallbackMethod = "fallback")
    public Optional<User> getUserDetails(@RequestParam Long userId) {
        // Retourner les détails de l'utilisateur
        return userService.getUserById(userId);
    }

    // Ajouter un utilisateur
    @PostMapping("/ajouter")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    // Récupérer tous les utilisateurs
    @GetMapping("/tous")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Récupérer un utilisateur par ID
    @GetMapping("/chercher/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Supprimer un utilisateur par ID
    @DeleteMapping("/supprimer/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
