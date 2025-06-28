package com.example.university.repository.custom.impl;

import com.example.university.model.dto.CouncilDto;
import com.example.university.model.dto.TopicDto;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.search.TopicSearch;
import com.example.university.repository.custom.CustomTopicRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomTopicRepositoryImpl implements CustomTopicRepository {
    private final EntityManager entityManager;

    @Override
    public List<TopicDto> getList(PageRequest<TopicSearch> request) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select topic.topic_id as topicId, " +
                "topic.proposer_id as proposerId, " +
                "topic.approver_id as approverId, " +
                "topic.title as title, " +
                "topic.topic_type as topicType, " +
                "topic.status as approverId, " +
                "topic.start_time as startTime, " +
                "topic.end_time as endTime, " +
                "topic.progress as progress, " +
                "topic.score as score, " +
                "proposer.name as proposerName, " +
                "approver.name as approverName " +
                "from university.topic topic ");

        queryBuilder.append("left join university.user proposer on proposer.user_id = topic.proposer_id ");
        queryBuilder.append("left join university.user approver on approver.user_id = topic.approver_id ");
        queryBuilder.append("where 1=1 ");

        if (request.getSearch().getTitle() != null) {
            queryBuilder.append("and topic.title ilike :title ");
        }

        if (request.getSearch().getScore() != null) {
            queryBuilder.append("and topic.score = :score ");
        }

        if (request.getSearch().getProgress() != null) {
            queryBuilder.append("and topic.progress = :progress ");
        }

        if (request.getSearch().getProposerId() != null) {
            queryBuilder.append("and topic.proposer_id = :proposerId ");
        }

        if (request.getSearch().getApproverId() != null) {
            queryBuilder.append("and topic.approver_id = :approverId ");
        }

        if (request.getSearch().getTopicType() != null) {
            queryBuilder.append("and topic.topic_type = :topicType ");
        }

        queryBuilder.append("order by topic.updated_at desc LIMIT :limit OFFSET :offset ");

        Query query = entityManager.createNativeQuery(queryBuilder.toString(), TopicDto.class);
        if (request.getSearch().getTitle() != null) {
            query.setParameter("title", "%" + request.getSearch().getTitle() + "%");
        }

        if (request.getSearch().getScore() != null) {
            query.setParameter("score", request.getSearch().getScore());
        }

        if (request.getSearch().getProgress() != null) {
            query.setParameter("progress", request.getSearch().getProgress());
        }

        if (request.getSearch().getProposerId() != null) {
            query.setParameter("proposerId", request.getSearch().getProposerId());
        }

        if (request.getSearch().getApproverId() != null) {
            query.setParameter("approverId", request.getSearch().getApproverId());
        }

        if (request.getSearch().getTopicType() != null) {
            query.setParameter("topicType", request.getSearch().getTopicType());
        }

        query.setParameter("limit", request.getLimit());
        query.setParameter("offset", (request.getPage() - 1) * request.getLimit());
        List<TopicDto> topicDtoList = query.getResultList();
        return topicDtoList;
    }

    public List<TopicDto> getAll(TopicSearch request) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select topic.topic_id as topicId, " +
                "topic.proposer_id as proposerId, " +
                "topic.approver_id as approverId, " +
                "topic.title as title, " +
                "topic.topic_type as topicType, " +
                "topic.status as approverId, " +
                "topic.start_time as startTime, " +
                "topic.end_time as endTime, " +
                "topic.progress as progress, " +
                "topic.score as score, " +
                "proposer.name as proposerName, " +
                "approver.name as approverName " +
                "from university.topic topic ");

        queryBuilder.append("left join university.user proposer on proposer.user_id = topic.proposer_id ");
        queryBuilder.append("left join university.user approver on approver.user_id = topic.approver_id ");
        queryBuilder.append("where 1=1 ");

        if (request.getTitle() != null) {
            queryBuilder.append("and topic.title ilike :title ");
        }

        if (request.getScore() != null) {
            queryBuilder.append("and topic.score = :score ");
        }

        if (request.getProgress() != null) {
            queryBuilder.append("and topic.progress = :progress ");
        }

        if (request.getProposerId() != null) {
            queryBuilder.append("and topic.proposer_id = :proposerId ");
        }

        if (request.getApproverId() != null) {
            queryBuilder.append("and topic.approver_id = :approverId ");
        }

        if (request.getTopicType() != null) {
            queryBuilder.append("and topic.topic_type = :topicType ");
        }

        queryBuilder.append("order by topic.updated_at desc");

        Query query = entityManager.createNativeQuery(queryBuilder.toString(), TopicDto.class);
        if (request.getTitle() != null) {
            query.setParameter("title", "%" + request.getTitle() + "%");
        }

        if (request.getScore() != null) {
            query.setParameter("score", request.getScore());
        }

        if (request.getProgress() != null) {
            query.setParameter("progress", request.getProgress());
        }

        if (request.getProposerId() != null) {
            query.setParameter("proposerId", request.getProposerId());
        }

        if (request.getApproverId() != null) {
            query.setParameter("approverId", request.getApproverId());
        }

        if (request.getTopicType() != null) {
            query.setParameter("topicType", request.getTopicType());
        }

        List<TopicDto> topicDtoList = query.getResultList();
        return topicDtoList;
    }

    @Override
    public Long count(PageRequest<TopicSearch> request) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select count(*) from university.topic topic ");
        queryBuilder.append("left join university.user proposer on proposer.user_id = topic.proposer_id ");
        queryBuilder.append("left join university.user approver on approver.user_id = topic.approver_id ");
        queryBuilder.append("where 1=1 ");

        if (request.getSearch().getTitle() != null) {
            queryBuilder.append("and topic.title = :title ");
        }

        if (request.getSearch().getScore() != null) {
            queryBuilder.append("and topic.score = :score ");
        }

        if (request.getSearch().getProgress() != null) {
            queryBuilder.append("and topic.progress = :progress ");
        }

        if (request.getSearch().getProposerId() != null) {
            queryBuilder.append("and topic.proposer_id = :proposerId ");
        }

        if (request.getSearch().getApproverId() != null) {
            queryBuilder.append("and topic.approver_id = :approverId ");
        }

        if (request.getSearch().getTopicType() != null) {
            queryBuilder.append("and topic.topic_type = :topicType ");
        }

        Query query = entityManager.createNativeQuery(queryBuilder.toString());

        if (request.getSearch().getTitle() != null) {
            query.setParameter("title", request.getSearch().getTitle());
        }

        if (request.getSearch().getScore() != null) {
            query.setParameter("score", request.getSearch().getScore());
        }

        if (request.getSearch().getProgress() != null) {
            query.setParameter("progress", request.getSearch().getProgress());
        }

        if (request.getSearch().getProposerId() != null) {
            query.setParameter("proposerId", request.getSearch().getProposerId());
        }

        if (request.getSearch().getApproverId() != null) {
            query.setParameter("approverId", request.getSearch().getApproverId());
        }

        if (request.getSearch().getTopicType() != null) {
            query.setParameter("topicType", request.getSearch().getTopicType());
        }

        return ((Number) query.getSingleResult()).longValue();
    }
}
