package com.example.keepocket2.data.service;

import com.example.keepocket2.data.APIResponse;
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
    Call<APIResponse<Category>> getCategoryList();

    @POST("categories")
    Call<Category> createCategory(@Body Category category);

    @PUT("categories/{idCategory}")
    Call<Category> updateCategory(@Path("idCategory") long idCategory, @Body Category Category);

    @PUT("limitupdate/{idCategory}")
    Call<Category> updateLimit(@Path("idCategory") long idCategory, @Body Category Category);

    @DELETE("categories/{categoryId}")
    Call<Category> deleteCategory(@Path("categoryId") long categoryId);
}
