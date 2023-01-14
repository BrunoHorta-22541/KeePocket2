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

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserDAO userDAO;
    private Repository repository;
    private LiveData<List<User>> allUsers;
    public UserViewModel(@NonNull Application application) {
        super(application);
        this.userDAO = Database.getInstance(application).getuserDAO();
        this.repository = new Repository(application.getApplicationContext());

    }
    public boolean createUserApi(String email, String password){
        User user = new User(0,email,password);
        String existingUser = repository.getUserByEmail(email);
        if (existingUser!= null) {
            Toast.makeText(getApplication().getApplicationContext(), "User already exists with this email!", Toast.LENGTH_LONG).show();
            return false;
        } else {
            this.repository.createUser(user);
            return true;
        }
    }
    public void updateUserApi(User user){
        this.repository.updateUser(user);
    }

    public LiveData<List<User>> getAllUsers() {
        return this.repository.listAllUsers();
    }

}
