package com.example.keepocket2.data.localDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.keepocket2.data.Movement;

import java.util.List;
@Dao
public interface MovementDAO {
    @Query("SELECT * FROM Movement")
    List<Movement> getAll();

    @Query("SELECT * FROM movement  WHERE idMovement = :movementsId")
    Movement getById(long movementsId);

    @Query("SELECT * FROM Movement WHERE idUser = :userId AND value>0")
    List<Movement> getIncome(long userId);

    /*
    @Query("SELECT * FROM Movements WHERE idUser = :userId AND value>0 AND MONTH('%m',movementsDate)  = :mm")
    List<Movements>  getIncomeByMM(long userId, int mm);


    @Query("SELECT * FROM Movements WHERE idUser = :userId AND strftime('%m',movementsDate) = strftime('%m',date('now')) AND value>0 ")
    List<Movements> getIncomeByMM(long userId);
     */

    @Query("SELECT * FROM Movement WHERE idUser = :userId AND value<0")
    List<Movement> getExpense(long userId);

    @Delete
    void delete(Movement movements);

    @Update
    void update(Movement movements);


    @Insert
    void insert(Movement movements);
}
