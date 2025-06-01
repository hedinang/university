package com.example.university.mapper;

import com.example.university.model.dto.CouncilMemberDto;
import com.example.university.model.entity.CouncilMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CouncilMemberMapper {
    @Mapping(source = "memberId", target = "userId")
    CouncilMemberDto councilMemberToCouncilMemberDto(CouncilMember councilMember);
}