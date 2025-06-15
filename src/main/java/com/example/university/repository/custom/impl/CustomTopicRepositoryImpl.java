package com.example.university.repository.custom.impl;

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

//        queryBuilder.append("select wp.* from wm_prjct wp ");
        queryBuilder.append("left join university.user proposer on proposer.user_id = topic.proposer_id ");
        queryBuilder.append("left join university.user approver on approver.user_id = topic.approver_id ");
//        queryBuilder.append("where council_member.council_role = :councilRole ");
//        queryBuilder.append("and wp.status = :status ");

        //limit offset
//        queryBuilder.append(" order by wp.prjct_sn desc ");
        queryBuilder.append("LIMIT :limit OFFSET :offset ");

        Query query = entityManager.createNativeQuery(queryBuilder.toString(), TopicDto.class);
//        query.setParameter("participantCode", participantCode);
//        query.setParameter("status", status);
//
//        if (projectName != null && !projectName.isEmpty()) {
//            query.setParameter("projectName", projectName);
//        }
//        query.setParameter("councilRole", "HOST");
        query.setParameter("limit", request.getLimit());
        query.setParameter("offset", (request.getPage() - 1) * request.getLimit());

        List<TopicDto> topicDtoList = query.getResultList();
        return topicDtoList;
    }

//    @Override
//    public Long count(PageRequest<TopicSearch> request) {
//        StringBuilder queryBuilder = new StringBuilder();
//        queryBuilder.append("select count(*) from council ");
//
//        Query query = entityManager.createNativeQuery(queryBuilder.toString());
//        return ((Number) query.getSingleResult()).longValue();
//    }
}
