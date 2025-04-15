package com.example.university.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @NotNull
    String organizationId;
    String organizationName;
    Long parentId;
    Integer position;
}
