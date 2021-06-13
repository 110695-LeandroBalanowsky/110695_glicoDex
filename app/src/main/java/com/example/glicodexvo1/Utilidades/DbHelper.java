package com.example.glicodexvo1.Utilidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DbHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME="glicodex_bd.db";
    private static final int DATABASE_VERSION= 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }
}
