package com.example.user_microservice.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.user_microservice.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
