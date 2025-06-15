package com.example.university.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@Entity
//@Table(name = "user")
@Table(schema = "university", name = "comment")
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @Column(name = "comment_id")
    private String commentId;

    @Column(name = "question_id")
    private String questionId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private Time date;
}
