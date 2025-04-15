package com.example.university.model.aggregation;

import lombok.Data;
import org.example.authentication.model.entity.OrganizationUser;
import org.example.authentication.model.entity.User;

@Data
public class AggOrganizationUser extends OrganizationUser {
    private User user;
}
