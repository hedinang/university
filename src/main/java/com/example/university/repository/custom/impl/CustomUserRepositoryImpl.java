package com.example.university.repository.custom.impl;

import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.search.FullUserSearch;
import com.example.university.repository.custom.CustomUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {
    private final EntityManager entityManager;

    public List<User> getPage(FullUserSearch request) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select * from user ");
//        queryBuilder.append(" where 1=1");

//        if (request.getIsActive() != null) {
//            queryBuilder.append(" AND status = ").append(request.getIsActive());
//        }

//        if (request.getExcludingUserIds() != null && !request.getExcludingUserIds().isEmpty()) {
//            queryBuilder.append(" AND user_sn NOT IN (:excludingUserIds)");
//        }

//        if (request.getUserName() != null && !request.getUserName().trim().isEmpty()) {
//            queryBuilder.append(" AND (");
////            queryBuilder.append(" UPPER(user_id) like '%").append(request.getUserName().toUpperCase()).append("%'");
//            queryBuilder.append(" or UPPER(username) like '%").append(request.getUserName().toUpperCase()).append("%'");
//            queryBuilder.append(" or UPPER(email) like '%").append(request.getUserName().toUpperCase()).append("%'");
////            queryBuilder.append(" or UPPER(user_telno) like '%").append(request.getUserName().toUpperCase()).append("%')");
//        }

//        queryBuilder.append(" order by user_id LIMIT :limit OFFSET :offset ");
//        queryBuilder.append(" LIMIT :limit OFFSET :offset ");
        Query query = entityManager.createNativeQuery(queryBuilder.toString(), User.class);

//        if (request.getExcludingUserIds() != null && !request.getExcludingUserIds().isEmpty()) {
//            query.setParameter("excludingUserIds", request.getExcludingUserIds());
//        }

//        query.setParameter("limit", request.getLimit());
//        query.setParameter("offset", request.getSkip());

        return query.getResultList();
    }

    @Override
    public List<User> getList(PageRequest<FullUserSearch> request) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select * from university.user u ");
        queryBuilder.append("where 1 = 1 ");

        if (request.getSearch().getTextSearch() != null && !request.getSearch().getTextSearch().isEmpty()) {
            queryBuilder.append("and u.name ilike :name ");
            queryBuilder.append("or u.email ilike :email ");
            queryBuilder.append("or u.phone ilike :phone ");
        }

        if (request.getSearch().getRole() != null && !request.getSearch().getRole().isEmpty()) {
            queryBuilder.append("and u.role_code = :role ");
        }

        queryBuilder.append("order by u.updated_at desc LIMIT :limit OFFSET :offset ");
        Query query = entityManager.createNativeQuery(queryBuilder.toString(), User.class);

        if (request.getSearch().getTextSearch() != null && !request.getSearch().getTextSearch().isEmpty()) {
            query.setParameter("name", "%" + request.getSearch().getTextSearch() + "%");
            query.setParameter("email", "%" + request.getSearch().getTextSearch() + "%");
            query.setParameter("phone", "%" + request.getSearch().getTextSearch() + "%");
        }

        if (request.getSearch().getRole() != null && !request.getSearch().getRole().isEmpty()) {
            query.setParameter("role", request.getSearch().getRole());
        }

        query.setParameter("limit", request.getLimit());
        query.setParameter("offset", (request.getPage() - 1) * request.getLimit());
        return query.getResultList();
    }

    @Override
    public Long count(PageRequest<FullUserSearch> request) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select count(*) from university.user u ");
        queryBuilder.append("where 1 = 1 ");

        if (request.getSearch().getTextSearch() != null && !request.getSearch().getTextSearch().isEmpty()) {
            queryBuilder.append("and u.name ilike :name ");
            queryBuilder.append("or u.email ilike :email ");
            queryBuilder.append("or u.phone ilike :phone ");
        }

        if (request.getSearch().getRole() != null && !request.getSearch().getRole().isEmpty()) {
            queryBuilder.append("and u.role_code = :role ");
        }

        Query query = entityManager.createNativeQuery(queryBuilder.toString());

        if (request.getSearch().getTextSearch() != null && !request.getSearch().getTextSearch().isEmpty()) {
            query.setParameter("name", "%" + request.getSearch().getTextSearch() + "%");
            query.setParameter("email", "%" + request.getSearch().getTextSearch() + "%");
            query.setParameter("phone", "%" + request.getSearch().getTextSearch() + "%");
        }

        if (request.getSearch().getRole() != null && !request.getSearch().getRole().isEmpty()) {
            query.setParameter("role", request.getSearch().getRole());
        }

        return ((Number) query.getSingleResult()).longValue();
    }
}
