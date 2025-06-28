package com.example.university.repository;

import com.example.university.model.entity.CouncilMember;
import com.example.university.repository.custom.CustomCouncilMemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouncilMemberRepository extends JpaRepository<CouncilMember, String>, CustomCouncilMemberRepository {
    List<CouncilMember> findByCouncilId(String councilId);

    List<CouncilMember> findByCouncilIdIn(List<String> councilIds);

    void deleteByCouncilId(String councilId);
}
