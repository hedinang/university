package com.example.university.model.request;

import lombok.Data;

@Data
public class UploadChunkReq {
    private String folder;
    private String base64;
    private int chunkIndex;
    private String contentType;
    private String requestUuid;

}
