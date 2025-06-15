package com.example.university.model.request.search;

import lombok.Data;

@Data
public class TopicSearch {
    String title;
    String topicType;
    String proposerId;
    String approverId;
    Integer progress;
    Double score;
}
