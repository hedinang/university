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
@Table(schema = "university", name = "council")
@AllArgsConstructor
@NoArgsConstructor
public class Council extends BaseEntity {
    @Id
    @Column(name = "council_id")
    private String councilId;

    @Column(name = "council_name")
    private String councilName;

    @Column(name = "year")
    private int year;
}
