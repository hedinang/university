package com.example.university.model.request;

import lombok.Data;

@Data
public class FCMTokenRequest {
    private String userId;
    private String token;
}
