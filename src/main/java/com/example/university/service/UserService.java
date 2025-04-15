package com.example.university.service;


import com.example.university.model.entity.User;
import com.example.university.model.request.ChangePasswordRequest;
import com.example.university.model.request.LoginRequest;
import com.example.university.model.request.UserRequest;
import com.example.university.model.request.search.UserSearch;

import java.util.List;
import java.util.Map;

public interface UserService {
    User changePassword(String userId, ChangePasswordRequest request);

    Map<String, Object> loginUser(LoginRequest request);

    boolean logout(String userId);

    List<User> createUser(List<User> users);

    User updateUser(User user, UserRequest userRequest);

    String forgetUserId(UserSearch userSearch);

    String forgetUserPassword(UserSearch userSearch);

    List<User> list(List<String> userIds);
}