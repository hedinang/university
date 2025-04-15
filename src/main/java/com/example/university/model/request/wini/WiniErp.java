package com.example.university.model.request.wini;

import lombok.Data;

@Data
public class WiniErp<T> {
    private String conn_sn;
    private String conn_type;
    private T data;
    private String create_date;
}
