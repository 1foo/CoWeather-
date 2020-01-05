package com.ifoo.weather.gson;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class UsersOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_USERS = "create table Users(" +
            "username text," +
            "password text" +
            ")";
    private Context Ucontext;

    public UsersOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        Ucontext = context;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USERS);
    }
}
