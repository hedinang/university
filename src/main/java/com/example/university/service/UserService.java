package com.example.university.service;


import com.example.university.model.dto.Page;
import com.example.university.model.entity.User;
import com.example.university.model.request.ChangePasswordRequest;
import com.example.university.model.request.LoginRequest;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.UserRequest;
import com.example.university.model.request.search.FullUserSearch;
import com.example.university.model.request.search.UserSearch;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    User changePassword(String userId, ChangePasswordRequest request);

    Map<String, Object> loginUser(LoginRequest request);

    User getMe(String userId);

    boolean logout(String userId);

    List<User> createUser(List<User> users);

    User updateUser(User user, UserRequest userRequest);

    String forgetUserId(UserSearch userSearch);

    String forgetUserPassword(UserSearch userSearch);

    List<User> list(List<String> userIds);

    Page<User> getPage(PageRequest<FullUserSearch> request);

    User create(User request, User user);

    User update(User request, User user);
}