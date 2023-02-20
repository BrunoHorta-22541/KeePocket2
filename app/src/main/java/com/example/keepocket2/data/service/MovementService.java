package com.example.keepocket2.data.service;

import com.example.keepocket2.data.Category;
import com.example.keepocket2.data.Movement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MovementService {

    @GET("Movement")
    Call<List<Movement>> getMovementList();

    @POST("Movement")
    Call<Movement> createMovement(@Body Movement newMovement);

    @PUT("Movements/{movementId}")
    Call<Movement> updateMovement(@Body Movement updatedMovement, @Path("movementId") long movementId);

    @DELETE("Movements/{movementId}")
    Call<Movement> deleteMovements(@Path("movementId") long movementId);
}
