package com.example.university.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageRequest<T> {
    private int limit;
    private int page;
    private T search;
}
