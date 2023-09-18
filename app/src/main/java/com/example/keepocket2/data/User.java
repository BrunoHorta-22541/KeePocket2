package com.example.keepocket2.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User{

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String email;
    private String name;
    private String email_verified_at;
    private String remember_token;
    private String created_at;
    private String updated_at;
    private String password;


    public User(long id, String name,String email, String email_verified_at,String password, String remember_token, String created_at, String updated_at) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.email_verified_at = email_verified_at;
        this.remember_token = remember_token;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public String getRemember_token() {
        return remember_token;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }


}
