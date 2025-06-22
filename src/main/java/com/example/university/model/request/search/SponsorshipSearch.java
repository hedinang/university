package com.example.university.model.request.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SponsorshipSearch {
    private String topicId;
    private String councilId;
    private Integer budget;
}
