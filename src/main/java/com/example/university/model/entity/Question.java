package com.example.university.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(schema = "university", name = "question")
@AllArgsConstructor
@NoArgsConstructor
public class Question extends BaseEntity {
    @Id
    @Column(name = "question_id")
    private String questionId;

    @Column(name = "questioner_id")
    private String questionerId;

    @Column(name = "recipient_id")
    private String recipientId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "last_comment_date")
    private String lastCommentDate;
}
