package com.example.keepocket2.data;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity
public class Category {

    @PrimaryKey(autoGenerate = true)
    private long idCategory;
    private long idUser;
    private String categoryName;
    private int limit;

    public Category(long idCategory, String categoryName, int limit, long idUser){
        this.idCategory = idCategory;
        this.categoryName = categoryName;
        this.limit = limit;
        this.idUser= idUser;
    }

    public long getIdCategory() {
        return idCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getLimit() {
        return limit;
    }

    public long getIdUser() {
        return idUser;
    }
}