package com.example.keepocket2.view.Session;


import com.example.keepocket2.data.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.keepocket2.data.User;
import com.example.keepocket2.viewmodel.UserViewModel;

public class LoginManager {
    private static UserViewModel userViewModel;
    private ViewModelProvider viewModelProvider;
    private static LiveData<List<User>> usersLiveData;
    public LoginManager(ViewModelProvider viewModelProvider) {
        this.viewModelProvider = viewModelProvider;
        userViewModel = viewModelProvider.get(UserViewModel.class);
        usersLiveData = userViewModel.getAllUsers();
    }

    private static Map<String, com.example.keepocket2.data.User> users;

    private static Map<String, com.example.keepocket2.data.User> getUsers() {
        if (users == null) {
            users = new HashMap<>();
            usersLiveData = userViewModel.getAllUsers();
            for (User user : usersLiveData.getValue()) {
                users.put(user.getEmail(), user);
            }
        }
        return users;
    }

    public static User validateUser(String username, String password) {
        com.example.keepocket2.data.User user = getUsers().get(username);
        if (user == null) return null;
        return user.getPassword().equals(password) ? user : null;

        /*if (user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }*/
    }
}