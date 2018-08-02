package com.example.acer.soccerinfo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MyContentProvider extends ContentProvider {
    private static final UriMatcher myuri = matchUri();
    MyDbHelper myDbHelper;
    SQLiteDatabase sqLiteDatabase;

    private static UriMatcher matchUri() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(FavouriteContract.AUTH, FavouriteContract.PATH, FavouriteContract.ROW_ID);
        uriMatcher.addURI(FavouriteContract.AUTH, FavouriteContract.PATH + "/#", FavouriteContract.TASK_ID);
        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        myDbHelper = new MyDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int match = myuri.match(uri);
        Cursor result = null;
        sqLiteDatabase = myDbHelper.getWritableDatabase();
        switch (match) {
            case FavouriteContract.ROW_ID:
                result = sqLiteDatabase.query(FavouriteContract.FavouriteContractEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case FavouriteContract.TASK_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "id=?";
                String[] mSelectionArgs = new String[]{"" + id};
                result = sqLiteDatabase.query(FavouriteContract.FavouriteContractEntry.TABLE_NAME, projection, mSelection, mSelectionArgs, null, null, sortOrder);
                break;
        }

        result.setNotificationUri(getContext().getContentResolver(), uri);
        return result;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri returnUri = null;
        int match = myuri.match(uri);
        sqLiteDatabase = myDbHelper.getWritableDatabase();
        if (match == FavouriteContract.ROW_ID) {
            long id = sqLiteDatabase.insert(FavouriteContract.FavouriteContractEntry.TABLE_NAME, null, values);
        } else {
            throw new UnsupportedOperationException(getContext().getResources().getString(R.string.unknoun_uri) + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = myuri.match(uri);
        long result;
        if (match == FavouriteContract.ROW_ID) {
            SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();
            result = sqLiteDatabase.delete(FavouriteContract.FavouriteContractEntry.TABLE_NAME, FavouriteContract.FavouriteContractEntry.COLUMN_ID + "=" + Integer.parseInt(selection), null);
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            throw new UnsupportedOperationException(getContext().getResources().getString(R.string.not_implemented));
        }
        return (int) result;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
