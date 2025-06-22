package com.example.university.repository.custom;

import com.example.university.model.entity.User;
import com.example.university.model.request.ResetUnreadRequest;

public interface CustomSeenRepository {
    void resetUnread(ResetUnreadRequest resetUnreadRequest, User user);
}
