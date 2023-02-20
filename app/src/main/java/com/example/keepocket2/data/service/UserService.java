package com.example.keepocket2.data.service;

import com.example.keepocket2.data.APIResponse;
import com.example.keepocket2.data.Category;
import com.example.keepocket2.data.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @GET("users")
    Call<APIResponse<User>> getUserList();

    @POST("users")
    Call<User> createUser(@Body User newUser);

    @PUT("users/{usersId}")
    Call<User> updateUser(@Body User updatedUser, @Path("usersId") int id);

    @DELETE("users/{usersId}")
    Call<User> deleteUser(@Path("usersId") int id);
}
