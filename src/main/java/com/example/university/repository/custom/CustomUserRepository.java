package com.example.university.repository.custom;

import com.example.university.model.entity.User;
import com.example.university.model.request.search.FullUserSearch;

import java.util.List;

public interface CustomUserRepository {
    List<User> getPage(FullUserSearch request);
}
