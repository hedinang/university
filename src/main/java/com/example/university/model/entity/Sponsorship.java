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
@Table(schema = "university", name = "sponsorship")
@AllArgsConstructor
@NoArgsConstructor
public class Sponsorship extends BaseEntity {
    @Id
    @Column(name = "sponsorship_id")
    private String sponsorshipId;

    @Column(name = "topic_id")
    private String topicId;

    @Column(name = "council_id")
    private String councilId;

    @Column(name = "budget")
    private Integer budget;

    @Column(name = "status")
    private String status;
}
