package com.example.university.repository.custom;

import com.example.university.model.dto.CouncilMemberDto;

import java.util.List;

public interface CustomCouncilMemberRepository {
    List<CouncilMemberDto> listCouncilMember(List<String> councilIds);
}
