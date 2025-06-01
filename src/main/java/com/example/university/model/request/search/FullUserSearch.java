package com.example.university.model.request.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullUserSearch {
    Integer limit;
    Long skip;
    String userName;
    List<String> excludingUserIds;
    Boolean isActive;
    String role;
}
