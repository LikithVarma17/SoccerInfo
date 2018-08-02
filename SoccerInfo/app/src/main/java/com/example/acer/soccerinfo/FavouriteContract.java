package com.example.acer.soccerinfo;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavouriteContract {
    public static final String AUTH = "com.example.acer.soccerinfo";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTH);
    public static final String PATH = "PlayerDetails";
    public static final int ROW_ID = 50;
    public static final int TASK_ID = 100;
    public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();

    public class FavouriteContractEntry implements BaseColumns {
        public static final String TABLE_NAME = "PlayerDetails";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_NAME = "Name";
        public static final String COLOUMN_TEAM = "Team";
        public static final String COLUMN_IMAGE = "Image";
        public static final String COLUMN_NATIONALITY = "Nationality";
        public static final String COLUMN_DESCRIPTION = "Description";
    }
}




