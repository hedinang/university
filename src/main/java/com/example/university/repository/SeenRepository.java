package com.example.university.repository;

import com.example.university.model.entity.Seen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeenRepository extends JpaRepository<Seen, String> {
    Seen findByUserId(String userId);

    Seen findByQuestionIdAndUserIdNot(String questionId, String userId);

    Seen findByQuestionIdAndUserId(String questionId, String userId);
}
