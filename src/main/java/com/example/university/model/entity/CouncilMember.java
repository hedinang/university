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
@Table(schema = "university", name = "council_member")
@AllArgsConstructor
@NoArgsConstructor
public class CouncilMember extends BaseEntity {
    @Id
    @Column(name = "council_member_id")
    private String councilMemberId;

    @Column(name = "council_id")
    private String councilId;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "council_role")
    private String councilRole;
}
