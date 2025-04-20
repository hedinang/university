package com.example.university.model.dto;

import com.example.university.model.entity.User;
import lombok.Data;

import java.util.Map;

@Data
public class Me {
    private User user;
    private Map<String, String> languageMap;
    private Map<String, Object> tour;
}
