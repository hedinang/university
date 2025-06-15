package com.example.university.repository;

import com.example.university.model.entity.Comment;
import com.example.university.repository.custom.CustomCommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String>, CustomCommentRepository {
}

