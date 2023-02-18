package com.example.keepocket2.view.Session;


import android.app.Application;
import android.content.Context;

import com.example.keepocket2.data.User;

import com.example.keepocket2.data.localDatabase.Database;
import com.example.keepocket2.data.localDatabase.UserDAO;
import com.example.keepocket2.viewmodel.UserViewModel;

import org.mindrot.jbcrypt.BCrypt;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginManager {
    private Application application;
    private static Executor executor = Executors.newSingleThreadExecutor();
    private static UserDAO userDAO;
    public LoginManager(Application application){

        this.userDAO = Database.getInstance(application).getuserDAO();
    }

    public static User validateUser(String email, String password) {
        User user;
        user = userDAO.getByEmail(email);

        String hashDeSenhaArmazenadoNoBD = user.getPassword();
        if(user == null){
            return null;
        }else if(BCrypt.checkpw(password, hashDeSenhaArmazenadoNoBD)){
            return user;
        }
        else {
            System.out.println("A senha está incorreta!");
            return null;
        }
    }
}

