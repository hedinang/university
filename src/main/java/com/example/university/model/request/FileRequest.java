package com.example.university.model.request;

import lombok.Data;

import java.util.List;

@Data
public class FileRequest {
    private List<String> subPaths;
    private String conversationId;
}
