package com.example.keepocket2.data.localDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.keepocket2.data.Category;
import com.example.keepocket2.data.Movement;
import com.example.keepocket2.data.ValueSum;

import java.util.List;
@Dao
public interface MovementDAO {
    @Query("SELECT * FROM Movement")
    List<Movement> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createMovements(List<Movement> movements);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovement(Movement movement);

    default void createOrUpdateMovements(List<Movement> movements) {
        for (Movement movement : movements) {
            insertMovement(movement);
        }
    }

    @Query("SELECT * FROM movement  WHERE idMovement = :movementsId")
    Movement getById(long movementsId);

    @Query("SELECT * FROM Movement WHERE idUser = :userId AND value>0")
    LiveData<List<Movement>> getIncome(long userId);

    /*
    @Query("SELECT * FROM Movements WHERE idUser = :userId AND value>0 AND MONTH('%m',movementsDate)  = :mm")
    List<Movements>  getIncomeByMM(long userId, int mm);


    @Query("SELECT * FROM Movements WHERE idUser = :userId AND strftime('%m',movementsDate) = strftime('%m',date('now')) AND value>0 ")
    List<Movements> getIncomeByMM(long userId);
     */

    @Query("SELECT * FROM Movement WHERE idUser = :userId AND value<0")
    LiveData<List<Movement>> getExpense(long userId);

    @Query("SELECT idCategory, SUM(value) FROM Movement WHERE idUser = :userId AND value<0 GROUP BY idCategory")
    List<ValueSum> getExpenseGroup(long userId);

    @Delete
    void delete(Movement movement);

    @Update
    void update(Movement movement);


    @Insert
    void insert(Movement movement);
}
