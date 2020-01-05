package com.ifoo.weather.gson;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UsersDatabase {

    private UsersOpenHelper helper;
    private List<User> users = new ArrayList<>();

    public UsersDatabase(UsersOpenHelper helper){
        this.helper = helper;
    }

    public List<User> getUsers(){

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("Users", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do{
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                User user = new User(username, password);
                users.add(user);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return users;
    }

    public void updateUser(User user){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", user.getPassword());
        db.update("Users", values, "username=?", new String[]{user.getUsername()});
    }

    public void addUser(User user){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        db.insert("Users", null, values);
    }
}
