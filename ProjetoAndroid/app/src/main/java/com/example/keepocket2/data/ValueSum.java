package com.example.keepocket2.data;

import androidx.room.ColumnInfo;

public class ValueSum {
    @ColumnInfo(name = "idCategory")
    public String categoryId;

    @ColumnInfo(name = "SUM(value)")
    public String sumValue;

    public String getCategoryId() {
        return categoryId;
    }

    public String getSumValue() {
        return sumValue;
    }
}
