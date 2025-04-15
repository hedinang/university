package com.example.university.model.request;

import lombok.Data;

@Data
public class ResourceRequest {
    private String folder;
    private String fileName;
    private String base64;
    private int chunkIndex;
    private int totalChunk;
    private String userId;
    private String conversationId; // optional
    private String type;
    private String requestUuid;
    private String contentType;
}
