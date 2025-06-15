package com.example.university.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private String questionId;
    private String questionerId;
    private String recipientId;
    private String title;
    private String content;
    private Date questionDate;
    private Date lastCommentDate;
    private Integer unread;

    private String questionerName;
    private String recipientName;

}
