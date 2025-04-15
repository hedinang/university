package com.example.university.model.dto;

import lombok.Data;

@Data
public class FullUserDto {
    private String userCode;
    private String userId;
    private String name;
    private String email;
    private String phone;
    private String birthday;
    private String language;
    private String avatar;
    private Long organization;
    private String mood;
    private String roleCode;
    private String createdAt;
    private String online;
}
