package com.example.keepocket2.data;

public class User {
    private long id;
    private String email;
    private String password;

    public User(long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }


    public User(long id, String email) {
        this.id = id;
        this.email = email;
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
}
