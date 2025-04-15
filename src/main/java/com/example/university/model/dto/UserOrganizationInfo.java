package com.example.university.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrganizationInfo {
    private Long objectCode;

    private String name;

    private String abbreviateName;

    private String parentCode;

    private String statusCode;

    private String objectId;

    private String parentId;

    private String type;

    private String paths;

    private Long userCode;
}
