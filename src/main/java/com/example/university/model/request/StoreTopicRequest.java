package com.example.university.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreTopicRequest {
    String topicId;
    String title;
    String proposerId;
    String approverId;
    String topicType;
    Date startTime;
    Date endTime;
    int progress;
    Double score;
    String status;
}
