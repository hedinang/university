package com.example.university.repository;

import com.example.university.model.entity.CouncilMember;
import com.example.university.repository.custom.CustomCouncilMemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouncilMemberRepository extends JpaRepository<CouncilMember, String>, CustomCouncilMemberRepository {
}
