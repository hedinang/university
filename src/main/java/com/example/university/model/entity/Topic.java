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
//@Table(name = "user")
@Table(schema = "university", name = "topic")
@AllArgsConstructor
@NoArgsConstructor
public class Topic extends BaseEntity {
    @Id
    @Column(name = "topic_id")
    private String topicId;

    @Column(name = "proposer_id")
    private String proposerId;

    @Column(name = "approver_id")
    private String approverId;

    @Column(name = "title")
    private String title;

    @Column(name = "topic_type")
    private String topicType;

    @Column(name = "status")
    private String status;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "progress")
    private Integer progress;

    @Column(name = "score")
    private Double score;

//    @Convert(converter = LocalDateTimeStringConverter.class)
//    @Column(name = "birthday")
//    private String birthday;
}
