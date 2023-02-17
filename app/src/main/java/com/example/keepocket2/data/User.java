package com.example.keepocket2.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User{

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String email;
    private String name;
    private String emailver;
    private String remeber;
    private long created;
    private long updated;
    private String password;


    public User(long id, String name,String email, String emailver,String password, String remeber, long created, long updated) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.emailver = emailver;
        this.remeber = remeber;
        this.created = created;
        this.updated = updated;
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

    public String getEmailver() {
        return emailver;
    }

    public String getRemeber() {
        return remeber;
    }

    public long getCreated() {
        return created;
    }

    public long getUpdated() {
        return updated;
    }
}
