package com.example.university.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto {
    private String topicId;
    private String proposerId;
    private String approverId;
    private String title;
    private String topicType;
    private String status;
    private Date startTime;
    private Date endTime;
    private Integer progress;
    private Double score;

    private String proposerName;
    private String approverName;
}
