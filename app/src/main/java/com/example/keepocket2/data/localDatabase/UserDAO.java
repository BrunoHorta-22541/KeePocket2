package com.example.keepocket2.data.localDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.keepocket2.data.Movement;
import com.example.keepocket2.data.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM User")
    LiveData<List<User>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createUser(List<User> user);

    @Query("SELECT * FROM User  WHERE id = :userid")
    User getById(long userid);

    @Query("DELETE FROM User")
    void clearTable();

    @Query("SELECT * FROM User  WHERE email = :email")
    User getByEmail(String email);

    @Delete
    void delete(User user);

    @Update
    void updateUser(User user);


    @Insert
    void insertUser(User user);

    @Insert
    long insertedUser(User user);


}

