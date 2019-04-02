package com.stereopt.gstocks.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.stereopt.gstocks.models.Stock.StockEntry;

public class StockDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "gstocks";

    private static final String SQL_CREATE_STOCKS =
        "CREATE TABLE " + StockEntry.TABLE_NAME + " (" +
            StockEntry._ID + " INTEGER PRIMARY KEY," +
            StockEntry.COLUMN_SYMBOL + " TEXT," +
            StockEntry.COLUMN_NAME + " TEXT," +
            StockEntry.COLUMN_REGION + " TEXT)";

    private static final String SQL_DELETE_STOCKS =
        "DROP TABLE IF EXISTS " + StockEntry.TABLE_NAME;

    public StockDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_STOCKS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_STOCKS);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
