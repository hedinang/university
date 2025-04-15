package com.example.university.model.aggregation;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.authentication.model.entity.OrganizationUser;

import java.util.List;

@Data
@AllArgsConstructor
public class AggOrganizationUserInfo extends OrganizationUser {
    public List<OrganizationUser> organizationUsers;
}
