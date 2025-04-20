package com.example.university.repository;

import com.example.university.model.entity.Council;
import com.example.university.repository.custom.CustomCouncilRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouncilRepository extends JpaRepository<Council, String>, CustomCouncilRepository {
}
