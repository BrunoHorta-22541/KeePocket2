package com.example.keepocket2.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.keepocket2.data.Category;
import com.example.keepocket2.data.localDatabase.CategoryDAO;
import com.example.keepocket2.data.localDatabase.Database;
import com.example.keepocket2.data.service.CategoryService;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5011/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private CategoryDAO categoryDAO;
    private CategoryService categoryService;
    public Repository(Context context){
        this.categoryDAO = Database.getInstance(context).getcategoryDAO();
        this.categoryService = retrofit.create(CategoryService.class);
    }
    private Executor executor = Executors.newSingleThreadExecutor();

    public LiveData<List<Category>> getCategoryFromId(long userId){
       return this.categoryDAO.getUserCategory(userId);
    }


    public void createCategory(Category category){
     executor.execute(() -> categoryDAO.insertCategory(category));
    }
    public void updateCategory(Category category){
        executor.execute(() -> categoryDAO.updateCategory(category));
    }

    public void refreshCategory() {
        this.categoryService.getCategoryList().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    executor.execute(() -> categoryDAO.createCategorys(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }
}
