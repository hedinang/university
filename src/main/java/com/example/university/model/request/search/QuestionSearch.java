package com.example.university.model.request.search;

import lombok.Data;

@Data
public class QuestionSearch {
    String title;
    String content;
    String questionerName;
    String recipientName;
}
