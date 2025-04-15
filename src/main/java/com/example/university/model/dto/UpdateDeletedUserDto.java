package com.example.university.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateDeletedUserDto {
    private List<String> userIds;

    private String eventType;
}
