package com.example.university.model.dto;

import com.example.university.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SponsorshipDbDto {
    private String sponsorshipId;
    private String topicId;
    private String councilId;
    private Integer budget;

    private String topicName;
    private String councilName;

    private String proposerId;
    private String approverId;
}
