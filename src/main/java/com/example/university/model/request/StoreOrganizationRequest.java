package com.example.university.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreOrganizationRequest {
    @NotNull
    Long organizationId;
    String organizationName;
    Long parentId;
    Integer position;
}
