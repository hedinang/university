package com.example.university.model.request.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullUserSearch {
    String textSearch;
    List<String> excludingUserIds;
    Boolean isActive;
    String role;
}
