package com.example.university.model.dto;


import lombok.Data;
import org.example.authentication.model.entity.OrgPath;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrganizationUserDto {
    public String name;
    private String abbreviateName;
    public String type;
    public List<OrgPath> paths = new ArrayList<>();
    public String status;
    public String objectId;
    public String parentId;
    public String userCode;
    public String avatar;
    public String mood;
    public String phone;
    public int position;
    public boolean online;

    public List<OrgPath> getPaths() {
        return paths;
    }
}
