package com.example.university.repository.custom.impl;

import com.example.university.model.dto.CommentDto;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.search.CommentSearch;
import com.example.university.repository.custom.CustomCommentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository {
    private final EntityManager entityManager;

    @Override
    public List<CommentDto> getList(PageRequest<CommentSearch> request) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select comment.comment_id as commentId, " +
                "comment.question_id as questionId, " +
                "comment.user_id as userId, " +
                "comment.content as content, " +
                "comment.created_at as date, " +
                "u.name as commentatorName " +
                "from university.comment comment ");

//        queryBuilder.append("select wp.* from wm_prjct wp ");
        queryBuilder.append("left join university.user u on u.user_id = comment.user_id ");
        queryBuilder.append("where comment.question_id = :questionId ");
        queryBuilder.append("and comment.status = :status ");

        //limit offset
//        queryBuilder.append(" order by wp.prjct_sn desc ");
        queryBuilder.append("order by comment.created_at asc LIMIT :limit OFFSET :offset ");

        Query query = entityManager.createNativeQuery(queryBuilder.toString(), CommentDto.class);
//        query.setParameter("participantCode", participantCode);
//        query.setParameter("status", status);
//
//        if (projectName != null && !projectName.isEmpty()) {
//            query.setParameter("projectName", projectName);
//        }
        query.setParameter("questionId", request.getSearch().getQuestionId());
        query.setParameter("status", "ACTIVE");
        query.setParameter("limit", request.getLimit());
        query.setParameter("offset", (request.getPage() - 1) * request.getLimit());

        List<CommentDto> commentDtoList = query.getResultList();
        return commentDtoList;
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
