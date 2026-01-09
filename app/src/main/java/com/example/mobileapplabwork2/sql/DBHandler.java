package com.example.mobileapplabwork2.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {

    public static String DatabaseName = "tbookdb.db",
            JMainTabl = "JMainTabl",
            T_idTabl = "_idTabl",
            TNameTab = "NameTab";

    public static Context databaseContext;
    public static SQLiteDatabase.CursorFactory factory = null;
    public static DBHandler DBHandler;

    public static SQLiteDatabase database;

    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        databaseContext = context;
        database = this.getWritableDatabase();
        DBHandler = this;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + JMainTabl + " (" + T_idTabl + " integer primary key, " + TNameTab + " text)");
        System.out.println("Sql created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("Sql upgraded");
    }
}
