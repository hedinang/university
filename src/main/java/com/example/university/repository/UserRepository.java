package com.example.university.repository;


import com.example.university.model.entity.User;
import com.example.university.repository.custom.CustomUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, CustomUserRepository {
    Optional<User> findByEmail(String email);

    boolean existsByUserId(String s);

    Optional<User> findByUserId(String userId);

    Optional<User> findByUsername(String username);

    List<User> findByUserIdIn(List<String> userIds);
}
