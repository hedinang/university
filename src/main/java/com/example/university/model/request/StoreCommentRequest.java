package com.example.university.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreCommentRequest {
    String questionId;
    String content;
    String commentId;
    String status;
}
