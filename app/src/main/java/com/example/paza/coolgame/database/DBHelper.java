package com.example.paza.coolgame.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.paza.coolgame.LoginActivity;


/**
 * Created by paza on 18.10.16.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE = "Login";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_WIN_GAME = "win_game";
    public static final String COLUMN_LOSE_GAME = "lose_game";
    private Cursor c;
    DBHelper dbHelper;
    SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1) {
            db.execSQL("CREATE TABLE Login (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT, "
                    + COLUMN_IMAGE + " TEXT, "
                    + COLUMN_WIN_GAME + " INTEGER DEFAULT 0, "
                    + COLUMN_LOSE_GAME + " INTEGER DEFAULT 0 );");
        }
        if (oldVersion > 1) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE);
            onCreate(db);
        }
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Login (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, "
                + COLUMN_IMAGE + " TEXT, "
                + COLUMN_WIN_GAME + " INTEGER DEFAULT 0, "
                + COLUMN_LOSE_GAME + " INTEGER DEFAULT 0 );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, oldVersion, newVersion);
    }

}
