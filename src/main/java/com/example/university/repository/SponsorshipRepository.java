package com.example.university.repository;

import com.example.university.model.entity.Sponsorship;
import com.example.university.repository.custom.CustomSponsorshipRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorshipRepository extends JpaRepository<Sponsorship, String>, CustomSponsorshipRepository {

}

