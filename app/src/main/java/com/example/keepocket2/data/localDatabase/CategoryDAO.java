package com.example.keepocket2.data.localDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.keepocket2.data.Category;

import java.util.List;
@Dao
public interface CategoryDAO {
    @Query("SELECT * FROM Category")
    List<Category> getAll();

    @Query("SELECT * FROM Category WHERE idCategory = :categoryId")
    Category getById(long categoryId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createCategorys(List<Category> category);

    @Query("SELECT * FROM Category WHERE idUser = :userId")
    LiveData<List<Category>> getUserCategories(long userId);


    @Query("SELECT categoryName FROM Category WHERE idUser = :userId")
    List<String> getUserCategoryName(long userId);

    @Query("SELECT * FROM Category WHERE idUser = :userId AND categoryName = :nameCategory")
    Category getCategoryByName(long userId, String nameCategory);

    @Query("SELECT * FROM Category WHERE idUser = :userId AND `limit` > 0 ")
    List<Category> getUserCategoryLimit(long userId);

    @Delete
    void delete(Category category);

    @Update
    void updateCategory(Category category);


    @Insert
    void insertCategory(Category category);

}