package com.example.university.model.request.search;

import lombok.Data;

import java.util.Date;

@Data
public class UserSearch {
    String userId;
    String name;
    String email;
    String phone;
    Date birthday;
}
