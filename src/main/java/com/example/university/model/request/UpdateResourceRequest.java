package com.example.university.model.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateResourceRequest {
    List<String> resourceIds;
    String conversationId;
}
