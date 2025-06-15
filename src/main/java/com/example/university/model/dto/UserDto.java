package com.example.university.model.dto;

import lombok.Data;

@Data
public class UserDto {
    private String userCode;
    private String userId;
    private String name;
    private String email;
    private String accessToken;
    private String language;
    private String avatar;
    private Long organization;
    private String roleCode;
    private String mood;
    private String createdAt;
    private String birthday;
}
