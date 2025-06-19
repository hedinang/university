package com.example.university.repository.custom.impl;

import com.example.university.model.dto.QuestionDto;
import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.search.QuestionSearch;
import com.example.university.repository.custom.CustomQuestionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CustomQuestionRepositoryImpl implements CustomQuestionRepository {
    private final EntityManager entityManager;

    @Override
    public List<QuestionDto> getList(PageRequest<QuestionSearch> request, User user) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select question.question_id as questionId, " +
                "question.questioner_id as questionerId, " +
                "question.recipient_id as recipientId, " +
                "question.title as title, " +
                "question.content as content, " +
                "question.created_at as questionDate, " +
                "question.last_comment_date as lastCommentDate, " +
                "seen.unread as unread, " +
                "questioner.name as questionerName, " +
                "recipient.name as recipientName " +
                "from university.question question ");

        queryBuilder.append("left join university.user questioner on questioner.user_id = question.questioner_id ");
        queryBuilder.append("left join university.user recipient on recipient.user_id = question.recipient_id ");
        queryBuilder.append("left join university.seen seen on seen.question_id = question.question_id ");
        queryBuilder.append("where seen.user_id = :userId ");

        if (request.getSearch().getTitle() != null && !request.getSearch().getTitle().isEmpty()) {
            queryBuilder.append("and question.title ilike :title ");
        }

        if (request.getSearch().getContent() != null && !request.getSearch().getContent().isEmpty()) {
            queryBuilder.append("and question.content ilike :content ");
        }

        if (request.getSearch().getQuestionerName() != null && !request.getSearch().getQuestionerName().isEmpty()
                && Objects.equals(user.getRoleCode(), "TEACHER")) {
            queryBuilder.append("and questioner.name ilike :questionerName ");
        }

        if (request.getSearch().getRecipientName() != null && !request.getSearch().getRecipientName().isEmpty()
                && Objects.equals(user.getRoleCode(), "STUDENT")) {
            queryBuilder.append("and recipient.name ilike :recipientName ");
        }

        queryBuilder.append("order by question.updated_at desc LIMIT :limit OFFSET :offset ");

        Query query = entityManager.createNativeQuery(queryBuilder.toString(), QuestionDto.class);
        query.setParameter("userId", user.getUserId());
        query.setParameter("limit", request.getLimit());
        query.setParameter("offset", (request.getPage() - 1) * request.getLimit());

        List<QuestionDto> questionDtoList = query.getResultList();
        return questionDtoList;
    }

    @Override
    public Long count(PageRequest<QuestionSearch> request, User user) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select count(*) from university.question question ");
        queryBuilder.append("left join university.user questioner on questioner.user_id = question.questioner_id ");
        queryBuilder.append("left join university.user recipient on recipient.user_id = question.recipient_id ");
        queryBuilder.append("left join university.seen seen on seen.question_id = question.question_id ");
        queryBuilder.append("where seen.user_id = :userId ");

        Query query = entityManager.createNativeQuery(queryBuilder.toString());
        query.setParameter("userId", user.getUserId());

        if (request.getSearch().getTitle() != null && !request.getSearch().getTitle().isEmpty()) {
            query.setParameter("title", request.getSearch().getTitle());
        }

        if (request.getSearch().getContent() != null && !request.getSearch().getContent().isEmpty()) {
            query.setParameter("content", request.getSearch().getContent());
        }

        if (request.getSearch().getQuestionerName() != null && !request.getSearch().getQuestionerName().isEmpty()
                && Objects.equals(user.getRoleCode(), "TEACHER")) {
            queryBuilder.append("and questioner.name ilike :questionerName ");
            query.setParameter("questionerName", request.getSearch().getQuestionerName());
        }

        if (request.getSearch().getRecipientName() != null && !request.getSearch().getRecipientName().isEmpty()
                && Objects.equals(user.getRoleCode(), "STUDENT")) {
            query.setParameter("recipientName", request.getSearch().getRecipientName());
        }

        return ((Number) query.getSingleResult()).longValue();
    }
}
