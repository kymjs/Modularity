package com.kymjs.app.memory.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kymjs.common.App;

/**
 * SQLiteOpenHelper
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "memory_box_db";

    public static final String TABLE_NAME = " MEMORY_BOX ";

    public static final String CREATE_MSG_TABLE = " create table "
            + TABLE_NAME
            + " (id integer primary key autoincrement not null, name text, key text,"
            + " value text, description text, image text, position integer)";


    public DatabaseHelper() {
        super(App.INSTANCE, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MSG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
