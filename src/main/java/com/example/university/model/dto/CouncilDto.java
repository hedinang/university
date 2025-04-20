package com.example.university.model.dto;

import com.example.university.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouncilDto {
    private String councilId;
    private String councilName;
    private int year;
    private String hostId;
    private String hostName;
    private String status;
    private String councilRole;
    private List<CouncilMemberDto> memberList;

    public CouncilDto(String councilId, String councilName, int year, String hostId, String hostName, String status, String councilRole) {
        this.councilId = councilId;
        this.councilName = councilName;
        this.year = year;
        this.hostId = hostId;
        this.hostName = hostName;
        this.status = status;
        this.councilRole = councilRole;
    }
}
