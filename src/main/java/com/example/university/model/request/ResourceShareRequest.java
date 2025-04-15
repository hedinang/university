package com.example.university.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceShareRequest {
    private List<String> resourceIds;
    private List<String> conversationIds;
}
