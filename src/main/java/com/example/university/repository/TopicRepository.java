package com.example.university.repository;

import com.example.university.model.entity.Topic;
import com.example.university.repository.custom.CustomTopicRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, String>, CustomTopicRepository {
}
