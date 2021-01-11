package com.renhen.optical_shop.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.renhen.optical_shop.data.OpticalShopContract.UserEntry;

public class OpticalShopDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME    = "opticalshop.db";
    private static final int    DATABASE_VERSION = 1;


    public OpticalShopDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_USER_TABLE = "CREATE TABLE " + UserEntry.TABLE_NAME + " ("
                + UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UserEntry.COLUMN_FIO + " TEXT NOT NULL, "
                + UserEntry.COLUMN_PHONE + " TEXT NOT NULL, "
                + UserEntry.COLUMN_BIRTHDATE + " INTEGER NOT NULL, "
                + UserEntry.COLUMN_LOGIN + " TEXT UNIQUE NOT NULL, "
                + UserEntry.COLUMN_PASSWORD + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+UserEntry.TABLE_NAME);
        onCreate(db);
    }
}
