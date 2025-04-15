package com.example.university.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrganizationChildrenRequest {
    private List<String> withoutObjectIds;
    private List<String> parentIds;
}
