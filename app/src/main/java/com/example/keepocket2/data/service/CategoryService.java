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

    @GET("Category")
    Call<List<Category>> getCategoryList();

    @POST("Category")
    Call<Category> createTicket(@Body Category newCategory);

    @PUT("Categorys/{categoryId}")
    Call<Category> updateTicket(@Body Category updatedCategory, @Path("categoryId") int categoryId);

    @DELETE("Category/{categoryId}")
    Call<Category> deleteTicket(@Path("categoryId") int categoryId);
}
