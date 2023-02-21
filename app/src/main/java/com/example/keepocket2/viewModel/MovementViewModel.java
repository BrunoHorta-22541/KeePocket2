package com.example.keepocket2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.keepocket2.data.Category;
import com.example.keepocket2.data.Movement;
import com.example.keepocket2.data.localDatabase.Database;
import com.example.keepocket2.data.localDatabase.MovementDAO;
import com.example.keepocket2.data.repository.Repository;

import java.util.List;

public class MovementViewModel extends AndroidViewModel {
    private MovementDAO movementDAO;
    private Repository repository;
    public MovementViewModel(@NonNull Application application) {
        super(application);
        this.movementDAO = Database.getInstance(application).getmovementsDAO();
        this.repository = new Repository(application.getApplicationContext());
    }
    public LiveData<List<Movement>> getExpenseById(long movementID){
        return this.repository.getExpenseFromId(movementID);
    }
    public LiveData<List<Movement>> getExpenseByIdGroup(long movementID){
        return this.repository.getExpenseFromIdGroup(movementID);
    }
    public LiveData<List<Movement>> getIncomeById(long movementID){
        return this.repository.getIncomeFromId(movementID);
    }
    public void refreshMovements(){
        this.repository.refreshMovement();
    }
    public void createMovementApi(Movement movement){
        this.repository.createMovementAPI(movement);
    }
    public void updateMovementApi(Category category){
        this.repository.updateCategory(category);
    }


    public void deleteMovement(long movementId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Movement movement = Database.getInstance(MovementViewModel.this.getApplication()).getmovementsDAO().getById(movementId);
                movementDAO.delete(movement);
            }
        }).start();
    }
}
