package com.example.university.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScreenRequest {
    @NotEmpty
    private String screenName;

    private String nextScreen;
}
