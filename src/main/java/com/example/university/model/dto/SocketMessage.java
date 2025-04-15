package com.example.university.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SocketMessage {
    private String expiredToken;
}
