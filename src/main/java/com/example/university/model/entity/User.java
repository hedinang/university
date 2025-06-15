package com.example.university.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
//@Table(name = "user")
@Table(schema = "university", name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "email")
    private String email;

    @Column(name = "language")
    private String language;

    @Column(name = "phone")
    private String phone;

    //    @Convert(converter = LocalDateTimeStringConverter.class)
    @Column(name = "birthday")
    private Date birthday;
}
