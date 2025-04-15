package com.example.university.model.dto;

import lombok.Data;
import org.example.authentication.model.entity.User;

import java.util.Map;

@Data
public class Me {
    private User user;
    private Map<String, String> languageMap;
    private Map<String, Object> tour;
}
