package com.example.university.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouncilMemberDto {
    private String councilId;
    private String userId;
    private String name;
    private String status;
    private String councilRole;


}
