package com.projekmobile.wihngeheng;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String ADDRESS = "ADDRESS";
    public static final String PHONE = "PHONE";
    public static final String ID = "ID";
    public static final String PASSWORD = "PASSWORD";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String name, String email, String address, String phone, String id){
        editor.putBoolean(LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putString(ADDRESS, address);
        editor.putString(PHONE, phone);
        editor.putString(ID, id);
        editor.apply();
    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin(){
        if (!this.isLoggin()){
            Intent i = new Intent(context, LogIn.class);
            context.startActivity(i);
            ((Profile)context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        //user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(ADDRESS, sharedPreferences.getString(ADDRESS, null));
        user.put(PHONE, sharedPreferences.getString(PHONE, null));
        user.put(ID, sharedPreferences.getString(ID, null));
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));
        return user;

    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LogIn.class);
        context.startActivity(i);
        ((Activity)context).finish();
    }

    //admin
    public void checkLoginAdmin(){
        if (!this.isLoggin()){
            Intent i = new Intent(context, LogIn.class);
            context.startActivity(i);
            ((AdminProfile)context).finish();
        }
    }

    public void logoutAdmin(){
        editor.clear();
        editor.commit();
        isLoggin();
        Intent i = new Intent(context, LogIn.class);
        context.startActivity(i);
        ((Activity)context).finish();
    }
}
