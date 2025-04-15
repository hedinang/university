package com.example.university.model.request.wini;

import lombok.Data;

import java.util.List;

@Data
public class WiniUser {
    private String id;
    private String name;
    private String phone;
    private String mail;
    private List<String> dept_id;
    private String active;
}
