package com.example.acer.soccerinfo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Database.db";

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void drop(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SportTeamDetails");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE "
                + FavouriteContract.FavouriteContractEntry.TABLE_NAME + " ( "
                + FavouriteContract.FavouriteContractEntry.COLUMN_ID + " TEXT PRIMARY KEY, "
                + FavouriteContract.FavouriteContractEntry.COLUMN_NAME + " TEXT  , "
                + FavouriteContract.FavouriteContractEntry.COLOUMN_TEAM + " TEXT  , "
                + FavouriteContract.FavouriteContractEntry.COLUMN_IMAGE + " TEXT  , "
                + FavouriteContract.FavouriteContractEntry.COLUMN_NATIONALITY + " TEXT  , "
                + FavouriteContract.FavouriteContractEntry.COLUMN_DESCRIPTION + " TEXT  ); ";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavouriteContract.FavouriteContractEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
