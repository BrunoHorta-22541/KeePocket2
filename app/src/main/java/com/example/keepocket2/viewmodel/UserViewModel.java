package com.example.keepocket2.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.keepocket2.data.User;
import com.example.keepocket2.data.localDatabase.Database;
import com.example.keepocket2.data.localDatabase.UserDAO;
import com.example.keepocket2.data.repository.Repository;

import org.mindrot.jbcrypt.BCrypt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class UserViewModel extends AndroidViewModel {
    private UserDAO userDAO;
    private Repository repository;
    private LiveData<List<User>> allUsers;
    public UserViewModel(@NonNull Application application) {
        super(application);
        this.userDAO = Database.getInstance(application).getuserDAO();
        this.repository = new Repository(application.getApplicationContext());

    }
    public void createUserApi(String email, String password){
        User existingUser = repository.getUserByEmail(email);
        if(existingUser == null){
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            String created=formatter.format(new Date(System.currentTimeMillis())) ;
            String updated= formatter.format(new Date(System.currentTimeMillis()));
            User user = new User(0,email,email,null,hashedPassword,null,created,updated);
            this.repository.createUser(user);
        }else{
            Toast.makeText(getApplication().getApplicationContext(), "User already exists with this email!", Toast.LENGTH_LONG).show();
        }
        /*
        if (existingUser.getEmail().equals(email)) {
            Toast.makeText(getApplication().getApplicationContext(), "User already exists with this email!", Toast.LENGTH_LONG).show();
        } else {
            User user = new User(0,email,password);
            this.repository.createUser(user);
        }*/
    }
    public void updateUserApi(User user){
        this.repository.updateUser(user);
    }
    public void refreshUser(){
        this.repository.refreshUser();
    }
    public LiveData<List<User>> getAllUsers() {
        return this.repository.listAllUsers();
    }
    public User getUserByEmail(String email){
        return this.repository.getUserByEmail(email);
    }

}
