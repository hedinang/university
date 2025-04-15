package com.example.university.model.request.search;

import lombok.Data;

@Data
public class SummaryFile {
    private String program;
    private String date;
    private Integer limit;
    private Integer page;
}
