package com.example.university.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(schema = "university", name = "seen")
@AllArgsConstructor
@NoArgsConstructor
public class Seen extends BaseEntity {
    @Id
    @Column(name = "seen_id")
    private String seenId;

    @Column(name = "question_id")
    private String questionId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "unread")
    private Integer unread;
}
