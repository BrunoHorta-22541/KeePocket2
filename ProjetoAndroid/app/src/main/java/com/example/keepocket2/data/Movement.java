package com.example.keepocket2.data;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Movement {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private long idMovement;
    private String idUser;
    private String idCategory;
    private String value;
    private String description;

    @SerializedName("movementsdate")
    private String movementsDate;



    public Movement(long idMovement, String idUser, String idCategory, String value, String description, String movementsDate){

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

    public String getIdUser() {
        return idUser;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public String getMovementsDate() {
        return movementsDate;
    }

}
