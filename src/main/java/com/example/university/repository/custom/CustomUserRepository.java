package com.example.university.repository.custom;

import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.search.CouncilSearch;
import com.example.university.model.request.search.FullUserSearch;

import java.util.List;

public interface CustomUserRepository {
    List<User> getList(PageRequest<FullUserSearch> request);

    Long count(PageRequest<FullUserSearch> request);
}
