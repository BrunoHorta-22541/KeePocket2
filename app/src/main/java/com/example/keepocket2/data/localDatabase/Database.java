package com.example.keepocket2.data.localDatabase;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.keepocket2.data.Category;
import com.example.keepocket2.data.Movement;

@androidx.room.Database(entities = {Movement.class, Category.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract MovementDAO getmovementsDAO();
    public abstract CategoryDAO getcategoryDAO();
    private static com.example.keepocket2.data.localDatabase.Database INSTANCE;
    public static com.example.keepocket2.data.localDatabase.Database getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            com.example.keepocket2.data.localDatabase.Database.class, "KeePocketDatabase").
                    allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

}