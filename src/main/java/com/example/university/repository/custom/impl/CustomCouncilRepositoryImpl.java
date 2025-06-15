package com.example.university.repository.custom.impl;

import com.example.university.model.dto.CouncilDto;
import com.example.university.model.dto.CouncilMemberDto;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.search.CouncilSearch;
import com.example.university.repository.CouncilMemberRepository;
import com.example.university.repository.custom.CustomCouncilRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomCouncilRepositoryImpl implements CustomCouncilRepository {
    private final EntityManager entityManager;

    private final CouncilMemberRepository councilMemberRepository;

    @Override
    public List<CouncilDto> getList(PageRequest<CouncilSearch> request) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select council.council_id as councilId, " +
                "council.council_name as councilName, " +
                "council.year as year, " +
                "u.user_id as hostId, " +
                "u.name as hostName, " +
                "council.status as status, " +
                "council_member.council_role as councilRole " +
                "from university.council council ");
        queryBuilder.append("left join university.council_member council_member on council.council_id = council_member.council_id ");
        queryBuilder.append("left join university.user u on u.user_id = council_member.member_id ");
        queryBuilder.append("where 1=1 ");

        if (request.getSearch().getMemberId() != null) {
            queryBuilder.append("and council_member.member_id = :memberId ");
        } else {
            queryBuilder.append("and council_member.council_role = :councilRole ");
        }

        if (request.getSearch().getCouncilName() != null && !request.getSearch().getCouncilName().isEmpty()) {
            queryBuilder.append("and council.council_name ilike :councilName ");
        }

        if (request.getSearch().getYear() != null) {
            queryBuilder.append("and council.year = :year ");
        }

        queryBuilder.append("order by council.updated_at desc LIMIT :limit OFFSET :offset ");

        Query query = entityManager.createNativeQuery(queryBuilder.toString(), CouncilDto.class);

        if (request.getSearch().getMemberId() != null) {
            query.setParameter("memberId", request.getSearch().getMemberId());
        } else {
            query.setParameter("councilRole", "HOST");
        }

        if (request.getSearch().getCouncilName() != null && !request.getSearch().getCouncilName().isEmpty()) {
            query.setParameter("councilName", "%" + request.getSearch().getCouncilName() + "%");
        }

        if (request.getSearch().getYear() != null) {
            query.setParameter("year", request.getSearch().getYear());
        }

        query.setParameter("limit", request.getLimit());
        query.setParameter("offset", (request.getPage() - 1) * request.getLimit());

        List<CouncilDto> councilDtoList = query.getResultList();
        List<String> councilIds = councilDtoList.stream().map(CouncilDto::getCouncilId).toList();

        List<CouncilMemberDto> councilMemberDtoList = councilMemberRepository.listCouncilMember(councilIds);
        Map<String, List<CouncilMemberDto>> councilMemGroup = councilMemberDtoList.stream()
                .collect(Collectors.groupingBy(CouncilMemberDto::getCouncilId));

        return councilDtoList.stream().peek(councilDto -> councilDto.setMemberList(councilMemGroup.get(councilDto.getCouncilId()))).toList();
    }

    @Override
    public Long count(PageRequest<CouncilSearch> request) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select count(*) from university.council council ");
        queryBuilder.append("left join university.council_member council_member on council.council_id = council_member.council_id ");
        queryBuilder.append("left join university.user u on u.user_id = council_member.member_id ");
        queryBuilder.append("where 1=1 ");

        if (request.getSearch().getMemberId() != null) {
            queryBuilder.append("and council_member.member_id = :memberId ");
        } else {
            queryBuilder.append("and council_member.council_role = :councilRole ");
        }

        if (request.getSearch().getCouncilName() != null && !request.getSearch().getCouncilName().isEmpty()) {
            queryBuilder.append("and council.council_name ilike :councilName ");
        }

        if (request.getSearch().getYear() != null) {
            queryBuilder.append("and council.year = :year ");
        }

        Query query = entityManager.createNativeQuery(queryBuilder.toString());

        if (request.getSearch().getMemberId() != null) {
            query.setParameter("memberId", request.getSearch().getMemberId());
        } else {
            query.setParameter("councilRole", "HOST");
        }

        if (request.getSearch().getCouncilName() != null && !request.getSearch().getCouncilName().isEmpty()) {
            query.setParameter("councilName", "%" + request.getSearch().getCouncilName() + "%");
        }

        if (request.getSearch().getYear() != null) {
            query.setParameter("year", request.getSearch().getYear());
        }
        return ((Number) query.getSingleResult()).longValue();
    }
}
