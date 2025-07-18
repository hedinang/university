package com.example.university.model.dto;

import com.example.university.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SponsorshipDto {
    private String sponsorshipId;
    private String topicId;
    private String councilId;
    private Integer budget;
    private String status;

    private String topicName;
    private String councilName;

    private String proposerId;
    private String approverId;

    private List<User> memberList;
    private User proposer;
    private User approver;
}
