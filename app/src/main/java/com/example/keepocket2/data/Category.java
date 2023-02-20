package com.example.keepocket2.data;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Category {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private long idCategory;
    private String idUser;
    private String categoryName;
    private String limit;

    public Category(long idCategory, String categoryName, String limit, String idUser){
        this.idCategory = idCategory;
        this.categoryName = categoryName;
        this.limit = limit;
        this.idUser = idUser;
    }

    public long getIdCategory() {
        return idCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getLimit() {
        return limit;
    }

    public String getIdUser() {
        return idUser;
    }



}