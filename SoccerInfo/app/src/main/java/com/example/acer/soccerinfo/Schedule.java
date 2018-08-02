package com.example.acer.soccerinfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Schedule extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressDialog dialog;
    ArrayList<JsonSchedule> jsonSchedule;
    MyScheduleAdapter myScheduleAdapter;
    LinearLayoutManager linearLayoutManager;
    int scroll;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!dialog.isShowing() && checkOnline()) {
            int scroll = linearLayoutManager.findFirstVisibleItemPosition();
            outState.putInt(getResources().getString(R.string.scrollpos), scroll);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        recyclerView = (RecyclerView) findViewById(R.id.schedule_recycler);
        linearLayoutManager = new LinearLayoutManager(this);
        dialog = new ProgressDialog(this);
        if (checkOnline()) {
            ScheduleAsync scheduleAsync = new ScheduleAsync();
            scheduleAsync.execute();
        } else {
            Toast.makeText(this, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
        }
        if (savedInstanceState != null && checkOnline()) {
            scroll = savedInstanceState.getInt(getResources().getString(R.string.scrollpos));
        }
    }

    public boolean checkOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null)
            return true;
        else
            return false;
    }

    public class ScheduleAsync extends AsyncTask<String, Void, String> {
        String response;
        String scheduleUrl;
        MyScheduleAdapter scheduleAdapter;

        public ScheduleAsync() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            scheduleUrl = getResources().getString(R.string.scheduleurl);
            jsonSchedule = new ArrayList<JsonSchedule>();
            dialog.setMessage(getResources().getString(R.string.Loading));
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Http connection = new Http();
            URL url = connection.buildUrl(scheduleUrl);
            try {
                response = connection.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                JSONArray jsonArray = jsonObject.getJSONArray(getResources().getString(R.string.events));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject schedule = jsonArray.getJSONObject(i);
                    String event = schedule.optString(getResources().getString(R.string.strEvent));
                    String date = schedule.optString(getResources().getString(R.string.strDate));
                    String time = schedule.optString(getResources().getString(R.string.strTime));
                    JsonSchedule jsonschedules = new JsonSchedule(event, date, time);
                    jsonSchedule.add(jsonschedules);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (dialog.isShowing())
                dialog.dismiss();
            myScheduleAdapter = new MyScheduleAdapter(Schedule.this, jsonSchedule);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(myScheduleAdapter);
            recyclerView.scrollToPosition(scroll);
        }
    }

}
