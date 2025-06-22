package com.example.university.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SponsorshipDto {
    private String sponsorshipId;
    private String topicId;
    private String councilId;
    private Integer budget;

    private String topicName;
    private String councilName;
}
