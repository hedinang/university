package com.example.university.repository;


import com.example.university.model.entity.User;
import com.example.university.repository.custom.CustomUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, CustomUserRepository {
    boolean existsByEmail(String s);

    boolean existsByUserId(String s);

    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(String userId);

    Optional<User> findByUserIdAndIsActive(String userId, boolean isActive);

    User findByAccessToken(String accessToken);

    List<User> findByUserIdIn(List<String> userIds);

    List<User> findByUserCodeIn(List<String> userCodes);

    @Modifying
    @Query("UPDATE User e SET e.avatar = :avatar WHERE e.userId = :userId")
    int updateAvatarByUserId(String avatar, String userId);

    Boolean existsByUserIdAndIsActive(String userId, boolean isActive);
}
