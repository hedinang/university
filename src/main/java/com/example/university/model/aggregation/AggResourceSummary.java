package com.example.university.model.aggregation;

import lombok.Data;

@Data
public class AggResourceSummary {
    String program;
    String date;
    String dataType;
    Long totalNumber;
    Long totalVolume;
}
