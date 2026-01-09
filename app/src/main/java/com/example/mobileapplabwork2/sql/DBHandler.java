package com.example.mobileapplabwork2.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {
    public static Context databaseContext;
    public static SQLiteDatabase.CursorFactory factory = null;
    public static DBHandler DBHandler;

    public static SQLiteDatabase database;

    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        databaseContext = context;
        database = this.getWritableDatabase();
        DBHandler = this;
        database.execSQL("CREATE TABLE IF NOT EXISTS JMainTabl (_idTabl integer primary key, NameTab text)");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
