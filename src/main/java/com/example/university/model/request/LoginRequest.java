package com.example.university.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {
    //    @Email(message = "UserId is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "UserId cannot be empty")
    private String userId;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
}
