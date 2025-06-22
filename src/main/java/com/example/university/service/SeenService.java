package com.example.university.service;

import com.example.university.model.entity.User;
import com.example.university.model.request.ResetUnreadRequest;

public interface SeenService {
    void resetUnread(ResetUnreadRequest resetUnreadRequest, User user);
}
