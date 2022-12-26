package com.example.keepocket2.data;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Movement {

    @PrimaryKey(autoGenerate = true)
    private long idMovement;
    private long idUser;
    private long idCategory;
    private int value;
    private String description;
    private long movementsDate;



    public Movement(long idMovement, long idUser, long idCategory, int value, String description, long movementsDate){

        this.idMovement = idMovement;
        this.idUser = idUser;
        this.idCategory = idCategory;
        this.value = value;
        this.description=description;
        this.movementsDate = movementsDate;

    }

    public long getIdMovement() {
        return idMovement;
    }

    public long getIdUser() {
        return idUser;
    }

    public long getIdCategory() {
        return idCategory;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public long getMovementsDate() {
        return movementsDate;
    }

}
