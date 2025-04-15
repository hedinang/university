package com.example.university.model.request.wini;

import lombok.Data;

@Data
public class WiniRequest<T> {
    private WiniErp<T> winierp;
}
