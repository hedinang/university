package com.example.university.repository.custom.impl;

import com.example.university.model.dto.CouncilMemberDto;
import com.example.university.repository.custom.CustomCouncilMemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomCouncilMemberRepositoryImpl implements CustomCouncilMemberRepository {
    private final EntityManager entityManager;

    @Override
    public List<CouncilMemberDto> listCouncilMember(List<String> councilIds) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select council_member.council_id as councilId, " +
                "u.user_id as userId, " +
                "u.name as name, " +
                "council_member.status as status, " +
                "council_member.council_role as councilRole " +
                "from university.council_member council_member ");
        queryBuilder.append("left join university.user u on u.user_id = council_member.member_id ");
        queryBuilder.append("where council_member.council_id in :councilIds");
        Query query = entityManager.createNativeQuery(queryBuilder.toString(), CouncilMemberDto.class);
        query.setParameter("councilIds", councilIds);
        return query.getResultList();
    }
}