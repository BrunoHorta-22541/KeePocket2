package com.example.keepocket2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.keepocket2.data.Category;
import com.example.keepocket2.data.localDatabase.CategoryDAO;
import com.example.keepocket2.data.localDatabase.Database;
import com.example.keepocket2.data.repository.Repository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryDAO categoryDao;
    private Repository repository;
    public CategoryViewModel(@NonNull Application application) {
        super(application);
        this.categoryDao = Database.getInstance(application).getcategoryDAO();
        this.repository = new Repository(application.getApplicationContext());
    }

    public LiveData<List<Category>> getCategoryById(long categoryID){
        return this.repository.getCategoryFromId(categoryID);
    }
    public LiveData<List<Category>> getAllCategories(){
        return this.repository.getALL();
    }


    public LiveData<List<Category>> getCategoryLimitById(long categoryID){
        return this.repository.getCategoryLimitFromId(categoryID);
    }
    public Category getCategoryWithID(long categoryID){
        return this.repository.getCategoryFromCategoryId(categoryID);
    }


    public void refreshCategory(){
        this.repository.refreshCategory();
    }

    public void createCategoryApi(Category category){
        this.repository.createCategoryAPI(category);
    }
    public void updateCategoryApi(Category category){
        this.repository.updateCategoryAPI(category);
    }


    public void deleteCategory(long categoryId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Category category = Database.getInstance(CategoryViewModel.this.getApplication()).getcategoryDAO().getById(categoryId);
                categoryDao.delete(category);
            }
        }).start();
    }
}
