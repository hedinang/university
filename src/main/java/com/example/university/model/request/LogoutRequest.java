package com.example.university.model.request;

import lombok.Data;

@Data
public class LogoutRequest {
    String fcmToken;
    String token;
}
