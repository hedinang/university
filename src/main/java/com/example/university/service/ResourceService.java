package com.example.university.service;

import com.example.university.model.dto.FileDto;
import org.springframework.http.ResponseEntity;

public interface ResourceService {
    FileDto storeFile(byte[] decodedBytes, String originalFileName);

    //    ResponseEntity getAvatar(String userId, String path);
    ResponseEntity<? extends Object> getResource(String path);
}
