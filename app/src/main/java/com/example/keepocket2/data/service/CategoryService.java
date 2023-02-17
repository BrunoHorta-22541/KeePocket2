package com.example.keepocket2.data.service;

import com.example.keepocket2.data.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoryService {

    @GET("categories")
    Call<List<Category>> getCategoryList();

    @POST("categories")
    Call<Category> createCategory(@Body Category newCategory);

    @PUT("categories/{categoryId}")
    Call<Category> updateCategory(@Body Category updatedCategory, @Path("categoryId") int categoryId);

    @DELETE("categories/{categoryId}")
    Call<Category> deleteCategory(@Path("categoryId") int categoryId);
}
