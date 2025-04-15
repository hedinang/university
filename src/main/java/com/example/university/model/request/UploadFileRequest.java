package com.example.university.model.request;

import lombok.Data;

@Data
public class UploadFileRequest {
    private String fileName;
    private String base64;
}
