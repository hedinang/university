package com.example.university.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private String commentId;
    private String questionId;
    private String userId;
    private String content;
    private Time date;

    private String commentatorName;
}
