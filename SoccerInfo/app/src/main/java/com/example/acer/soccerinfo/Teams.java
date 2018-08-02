package com.example.acer.soccerinfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Teams extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressDialog dialog;
    GridLayoutManager gridLayoutManager;
    ArrayList<JsonTeam> jsonTeams;
    int scroll;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!dialog.isShowing() && checkOnline()) {
            int scroll = gridLayoutManager.findFirstVisibleItemPosition();
            outState.putInt(getResources().getString(R.string.scrollpos), scroll);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        dialog = new ProgressDialog(this);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        if (checkOnline()) {
            TeamAsync teamAsync = new TeamAsync(getResources().getString(R.string.myurl));
            teamAsync.execute();
        } else {
            Toast.makeText(this, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
        }
        if (savedInstanceState != null && checkOnline()) {
            scroll = savedInstanceState.getInt(getResources().getString(R.string.scrollpos));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog.isShowing())
            dialog.dismiss();
    }

    public boolean checkOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null)
            return true;
        else
            return false;
    }

    public class TeamAsync extends AsyncTask<String, Void, String> {
        String response;
        String teamUrl = "";
        MyTeamAdapter teamAdapter;

        public TeamAsync(String s) {
            this.teamUrl = s;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jsonTeams = new ArrayList<JsonTeam>();
            dialog.setMessage(getResources().getString(R.string.loading));
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Http connection = new Http();
            URL url = connection.buildUrl(teamUrl);
            try {
                response = connection.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                JSONArray jsonArray = jsonObject.getJSONArray(getResources().getString(R.string.results));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject team = jsonArray.getJSONObject(i);
                    String name = team.optString(getResources().getString(R.string.strTeam));
                    String id = team.optString(getResources().getString(R.string.idTeam));
                    String shortname = team.optString(getResources().getString(R.string.strTeamShort));
                    String description = team.optString(getResources().getString(R.string.strDescriptionEN));
                    String othername = team.optString(getResources().getString(R.string.strAlternate));
                    String website = team.optString(getResources().getString(R.string.strWebsite));
                    String image = team.optString(getResources().getString(R.string.strTeamBadge));
                    JsonTeam jsonTeam = new JsonTeam(name, image, othername, id, shortname, website, description);
                    jsonTeams.add(jsonTeam);
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
            teamAdapter = new MyTeamAdapter(Teams.this, jsonTeams);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(teamAdapter);
            recyclerView.scrollToPosition(scroll);
        }
    }
}
