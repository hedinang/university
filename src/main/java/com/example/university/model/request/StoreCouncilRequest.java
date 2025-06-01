package com.example.university.model.request;

import com.example.university.model.entity.CouncilMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreCouncilRequest {
    String councilId;
    String councilName;
    int year;
    String status;
    List<CouncilMember> teacherList;
}
