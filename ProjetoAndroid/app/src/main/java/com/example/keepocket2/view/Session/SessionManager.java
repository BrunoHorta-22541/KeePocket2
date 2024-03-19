package com.example.keepocket2.view.Session;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.keepocket2.data.User;

public class SessionManager {

    private SessionManager() {

    }


    private static SharedPreferences sharedPreferences;

    private static SharedPreferences getSharedPreferences(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("sessionPreferences", Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }
    public static void saveSession(Context context, String username, boolean rememberMe, long userId, String password, String name, String ver, String rem, String created, String updated) {
        getSharedPreferences(context)
                .edit()
                .putString("username", username)
                .putLong("userId", userId)
                .putString("password", password)
                .putString("name",name)
                .putString("verified",ver)
                .putString("remember",rem)
                .putString("created",created)
                .putString("updated",updated)
                .putBoolean("rememberMe", rememberMe).apply();
    }

    public static User getActiveSession(Context context) {
        String username = getSharedPreferences(context).getString("username", null);
        String password = getSharedPreferences(context).getString("password", null);
        String name = getSharedPreferences(context).getString("name", null);
        String verif = getSharedPreferences(context).getString("verified", null);
        String remeb = getSharedPreferences(context).getString("remember", null);
        String created =getSharedPreferences(context).getString("created", null);
        String updated =getSharedPreferences(context).getString("updated", null);
        long userId = getSharedPreferences(context).getLong("userId", 0);
        return new User(userId,username,password,name,verif,remeb,created,updated);
    }

    public static boolean sessionExists(Context context) {
        String loggedInUser = getSharedPreferences(context).getString("username", null);
        return loggedInUser != null;
    }

    public static boolean persistedSession(Context context) {
        return getSharedPreferences(context).getBoolean("rememberMe", false);
    }

    public static void clearSession(Context context) {
        getSharedPreferences(context).edit().clear().apply();
    }
}
