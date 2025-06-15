package com.example.university.repository;

import com.example.university.model.entity.Question;
import com.example.university.repository.custom.CustomQuestionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, String>, CustomQuestionRepository {
}
