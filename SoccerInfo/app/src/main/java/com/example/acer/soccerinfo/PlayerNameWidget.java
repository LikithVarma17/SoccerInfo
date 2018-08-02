package com.example.acer.soccerinfo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class PlayerNameWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Cursor cursor;
        String text;
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.player_name_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.PlayerPref), 0);
        if (sharedPreferences.getBoolean(context.getResources().getString(R.string.Logged), false)) {
            views.setTextViewText(R.id.appwidget_text, context.getResources().getString(R.string.loggedoff));
            appWidgetManager.updateAppWidget(appWidgetId, views);
        } else {
            cursor = context.getContentResolver().query(FavouriteContract.CONTENT_URI, null, null, null, null);
            text = "";
            while (cursor.moveToNext()) {
                text = text + "\n" + cursor.getString(1);
            }        // Instruct the widget manager t// o update the widget
            cursor.close();
            if (text.equals("")) {
                views.setTextViewText(R.id.appwidget_text, context.getResources().getString(R.string.NoFavourites));
                appWidgetManager.updateAppWidget(appWidgetId, views);

            } else {
                views.setTextViewText(R.id.appwidget_text, text);
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        }    // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

