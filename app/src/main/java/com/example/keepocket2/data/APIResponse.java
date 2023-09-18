package com.example.keepocket2.data;

import java.util.List;

public class APIResponse<T> {
    List<T> data;

    public APIResponse(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }
}
