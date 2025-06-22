package com.example.university.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreSponsorshipRequest {
    private String sponsorshipId;
    private String topicId;
    private String councilId;
    private Integer budget;
}
