package com.example.university.service.custom;

import com.example.university.model.dto.FileDto;
import com.example.university.service.ResourceService;
import com.example.university.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    public FileDto storeFile(byte[] decodedBytes, String originalFileName) {
        try {
            UUID uuid = UUID.randomUUID();
            String storeFileName = String.format("%s%s", uuid, StringUtil.getFileExtension(originalFileName));
            String fileName = String.format("store/%s%s", uuid, StringUtil.getFileExtension(originalFileName));
            FileUtils.writeByteArrayToFile(new File(fileName), decodedBytes);
            return new FileDto(storeFileName, originalFileName);
        } catch (IOException e) {
            log.error("Cannot save file from queue: chat.file", e);
            return null;
        }
    }

//    @Override
//    public ResponseEntity getAvatar(String path) {
//        return getResource(subPath);
//    }

    public ResponseEntity<? extends Object> getResource(String path) {
        InputStream fileInputStream;
        try {
            Path filePath = new File("store/" + path).toPath();
            fileInputStream = new FileInputStream(filePath.toFile());

            String mimeType = Files.probeContentType(filePath);

            if (mimeType == null || mimeType.equals("application/octet-stream")) {
                // Manually detect .hwp
                if (!filePath.toString().contains(".")) {
                    mimeType = "application/octet-stream";
                } else {
                    String[] splittingFiles = filePath.toString().split("\\.");
                    mimeType = String.format("application/x-%s", splittingFiles[splittingFiles.length - 1]);
                }

            }

            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(mimeType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath.getFileName().toString() + "\"")
                    .contentLength(Files.size(filePath))
                    .body(new InputStreamResource(fileInputStream));

        } catch (IOException e) {
            log.error("can not download", e);
            return ResponseEntity.ok(null);
        }
    }
}
