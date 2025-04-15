package com.example.university.model.request;

import lombok.Data;

import java.util.List;

@Data
public class OrganizationUserSearch {
    private String name;
    private List<String> existedMemberIds;
    private boolean setting;
}
