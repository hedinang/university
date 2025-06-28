package com.example.university.repository.custom.impl;

import com.example.university.mapper.SponsorshipMapper;
import com.example.university.model.dto.SponsorshipDbDto;
import com.example.university.model.dto.SponsorshipDto;
import com.example.university.model.entity.CouncilMember;
import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.search.SponsorshipSearch;
import com.example.university.repository.CouncilMemberRepository;
import com.example.university.repository.UserRepository;
import com.example.university.repository.custom.CustomSponsorshipRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomSponsorshipRepositoryImpl implements CustomSponsorshipRepository {
    private final EntityManager entityManager;
    private final CouncilMemberRepository councilMemberRepository;
    private final UserRepository userRepository;
    private final SponsorshipMapper sponsorshipMapper;

    @Override
    public List<SponsorshipDto> getList(PageRequest<SponsorshipSearch> request, User user) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select sponsorship.sponsorship_id as sponsorshipId, " +
                "sponsorship.topic_id as topicId, " +
                "sponsorship.council_id as councilId, " +
                "sponsorship.budget as budget, " +
                "topic.title as topicName, " +
                "council.council_name as councilName, " +
                "topic.proposer_id as proposerId, " +
                "topic.approver_id as approverId " +
                "from university.sponsorship sponsorship ");

        queryBuilder.append("left join university.council council on council.council_id = sponsorship.council_id ");
        queryBuilder.append("left join university.topic topic on topic.topic_id = sponsorship.topic_id ");
        queryBuilder.append("left join university.council_member councilMember on councilMember.council_id = sponsorship.council_id ");
        queryBuilder.append("where councilMember.member_id = :memberId ");

//        if (request.getSearch().getTitle() != null && !request.getSearch().getTitle().isEmpty()) {
//            queryBuilder.append("and question.title ilike :title ");
//        }
//
//        if (request.getSearch().getContent() != null && !request.getSearch().getContent().isEmpty()) {
//            queryBuilder.append("and question.content ilike :content ");
//        }
//
//        if (request.getSearch().getQuestionerName() != null && !request.getSearch().getQuestionerName().isEmpty()
//                && Objects.equals(user.getRoleCode(), "TEACHER")) {
//            queryBuilder.append("and questioner.name ilike :questionerName ");
//        }
//
//        if (request.getSearch().getRecipientName() != null && !request.getSearch().getRecipientName().isEmpty()
//                && Objects.equals(user.getRoleCode(), "STUDENT")) {
//            queryBuilder.append("and recipient.name ilike :recipientName ");
//        }

        queryBuilder.append("order by sponsorship.updated_at desc LIMIT :limit OFFSET :offset ");

        Query query = entityManager.createNativeQuery(queryBuilder.toString(), SponsorshipDbDto.class);
        query.setParameter("memberId", user.getUserId());
        query.setParameter("limit", request.getLimit());
        query.setParameter("offset", (request.getPage() - 1) * request.getLimit());

        List<SponsorshipDbDto> sponsorshipDbDtoList = query.getResultList();
        List<String> proposerIds = sponsorshipDbDtoList.stream().map(SponsorshipDbDto::getProposerId).toList();
        List<String> approverIds = sponsorshipDbDtoList.stream().map(SponsorshipDbDto::getApproverId).toList();


        List<String> councilIds = sponsorshipDbDtoList.stream().map(SponsorshipDbDto::getCouncilId).toList();
        List<CouncilMember> councilMembers = councilMemberRepository.findByCouncilIdIn(councilIds);
        Map<String, List<CouncilMember>> councilMemberMap = councilMembers.stream().collect(Collectors.groupingBy(CouncilMember::getCouncilId));
        List<String> userIds = new java.util.ArrayList<>(councilMembers.stream().map(CouncilMember::getMemberId).toList());
        userIds.addAll(proposerIds);
        userIds.addAll(approverIds);

        List<User> users = userRepository.findByUserIdIn(userIds);
        Map<String, User> userMap = users.stream().collect(Collectors.toMap(User::getUserId, u -> u));

        return sponsorshipDbDtoList.stream().map(sponsorshipDbDto -> {
            SponsorshipDto sponsorshipDto = sponsorshipMapper.toSponsorshipDto(sponsorshipDbDto);

            sponsorshipDto.setProposer(userMap.get(sponsorshipDto.getProposerId()));
            sponsorshipDto.setApprover(userMap.get(sponsorshipDto.getApproverId()));

            List<CouncilMember> councilMemberList = councilMemberMap.get(sponsorshipDto.getCouncilId());
            List<User> us = councilMemberList.stream().map(councilMember -> userMap.get(councilMember.getMemberId())).toList();
            sponsorshipDto.setMemberList(us);
            return sponsorshipDto;
        }).toList();
    }

    @Override
    public Long count(PageRequest<SponsorshipSearch> request, User user) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select count(*) from university.sponsorship sponsorship ");
        queryBuilder.append("left join university.council council on council.council_id = sponsorship.council_id ");
        queryBuilder.append("left join university.topic topic on topic.topic_id = sponsorship.topic_id ");
        queryBuilder.append("left join university.council_member councilMember on councilMember.council_id = sponsorship.council_id ");
        queryBuilder.append("where councilMember.member_id = :memberId ");

        Query query = entityManager.createNativeQuery(queryBuilder.toString());
        query.setParameter("memberId", user.getUserId());

//        if (request.getSearch().getTitle() != null && !request.getSearch().getTitle().isEmpty()) {
//            query.setParameter("title", request.getSearch().getTitle());
//        }
//
//        if (request.getSearch().getContent() != null && !request.getSearch().getContent().isEmpty()) {
//            query.setParameter("content", request.getSearch().getContent());
//        }
//
//        if (request.getSearch().getQuestionerName() != null && !request.getSearch().getQuestionerName().isEmpty()
//                && Objects.equals(user.getRoleCode(), "TEACHER")) {
//            queryBuilder.append("and questioner.name ilike :questionerName ");
//            query.setParameter("questionerName", request.getSearch().getQuestionerName());
//        }
//
//        if (request.getSearch().getRecipientName() != null && !request.getSearch().getRecipientName().isEmpty()
//                && Objects.equals(user.getRoleCode(), "STUDENT")) {
//            query.setParameter("recipientName", request.getSearch().getRecipientName());
//        }

        return ((Number) query.getSingleResult()).longValue();
    }
}
