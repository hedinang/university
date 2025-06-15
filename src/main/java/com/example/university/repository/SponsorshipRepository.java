package com.example.university.repository;

import com.example.university.model.entity.Sponsorship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorshipRepository extends JpaRepository<Sponsorship, String> {
}

