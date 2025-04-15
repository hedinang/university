package com.example.university.model.request;

import lombok.Data;

@Data
public class RemoveUserOrgRequest {
    String objectId;
    String parentId;
}
