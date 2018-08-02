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

public class Result extends AppCompatActivity {

    ProgressDialog dialog;
    int scroll;
    ArrayList<JsonResult> jsonResults;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MyResultAdapter myResultAdapter;

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
        setContentView(R.layout.activity_result);
        dialog = new ProgressDialog(this);
        recyclerView = findViewById(R.id.result_recycler);
        linearLayoutManager = new LinearLayoutManager(this);
        if (checkOnline()) {
            ResultAsync resultAsync = new ResultAsync();
            resultAsync.execute();
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

    public class ResultAsync extends AsyncTask<String, Void, String> {
        String response;
        String resultUrl;

        public ResultAsync() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            resultUrl = getResources().getString(R.string.resulturl);
            jsonResults = new ArrayList<JsonResult>();
            dialog.setMessage(getResources().getString(R.string.loading));
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Http connection = new Http();
            URL url = connection.buildUrl(resultUrl);
            try {
                response = connection.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                JSONArray jsonArray = jsonObject.getJSONArray(getResources().getString(R.string.table));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject result = jsonArray.getJSONObject(i);
                    String name = result.optString(getResources().getString(R.string.name));
                    int played = result.optInt(getResources().getString(R.string.played));
                    int win = result.optInt(getResources().getString(R.string.win));
                    int loss = result.optInt(getResources().getString(R.string.loss));
                    int draw = result.optInt(getResources().getString(R.string.draw));
                    JsonResult jsonResult = new JsonResult(name, played, win, loss, draw);
                    jsonResults.add(jsonResult);
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
            myResultAdapter = new MyResultAdapter(Result.this, jsonResults);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(myResultAdapter);
            recyclerView.scrollToPosition(scroll);
        }
    }
}
