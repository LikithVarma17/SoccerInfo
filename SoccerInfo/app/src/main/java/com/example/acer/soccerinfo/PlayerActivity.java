package com.example.acer.soccerinfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {
    ProgressDialog dialog;
    ArrayList<JsonPlayer> jsonPlayers;
    RecyclerView recyclerView;
    String purl;
    int scroll;
    PlayerAdapter playerAdapter;
    GridLayoutManager gridLayoutManager;
    ArrayList<JsonPlayer> favJsonPlayers;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!dialog.isShowing() && checkOnline()) {
            int scroll = gridLayoutManager.findFirstVisibleItemPosition();
            outState.putInt(getResources().getString(R.string.scrollpos), scroll);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.fav:
                if (checkOnline())
                    getSupportLoaderManager().restartLoader(10, null, this);
                else
                    Toast.makeText(this, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        purl = getResources().getString(R.string.playerUrl);
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString(getResources().getString(R.string.ID));
        gridLayoutManager = new GridLayoutManager(PlayerActivity.this, 2);
        dialog = new ProgressDialog(this);
        purl = purl + id;
        recyclerView = findViewById(R.id.playerrecycler);
        if (checkOnline()) {
            PlayerAsync playerAsync = new PlayerAsync();
            playerAsync.execute();
        } else {
            Toast.makeText(this, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
        }
        if (savedInstanceState != null && checkOnline()) {
            scroll = savedInstanceState.getInt(getResources().getString(R.string.scrollpos));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.fav, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle bundle) {
        return new AsyncTaskLoader<String>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }

            @Override
            public String loadInBackground() {
                Cursor cursor;
                favJsonPlayers = new ArrayList<JsonPlayer>();
                cursor = getContentResolver().query(FavouriteContract.CONTENT_URI, null, null, null, null);
                while (cursor.moveToNext()) {
                    JsonPlayer jsonPlayer = new JsonPlayer(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                    favJsonPlayers.add(jsonPlayer);
                }
                cursor.close();
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object data) {
        if (favJsonPlayers.size() > 0) {
            playerAdapter = new PlayerAdapter(PlayerActivity.this, favJsonPlayers);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.setAdapter(playerAdapter);

        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(PlayerActivity.this);
            alert.setMessage(R.string.Message).setTitle(R.string.NoFavourites);
            alert.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                    Intent intent = new Intent(PlayerActivity.this, TeamDetailsDisplayer.class);
                    startActivity(intent);
                }
            });
            AlertDialog dialog = alert.create();
            dialog.show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }

    public boolean checkOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null)
            return true;
        else
            return false;
    }

    public class PlayerAsync extends AsyncTask<String, Void, String> {
        String response;
        PlayerAdapter playerAdapter;

        public PlayerAsync() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jsonPlayers = new ArrayList<JsonPlayer>();
            dialog.setMessage(getResources().getString(R.string.Loading));
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Http connection = new Http();
            URL url = connection.buildUrl(purl);
            try {
                response = connection.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                JSONArray jsonArray = jsonObject.getJSONArray(getResources().getString(R.string.player));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject player = jsonArray.getJSONObject(i);
                    String name = player.optString(getResources().getString(R.string.strPlayer));
                    String teamname = player.optString(getResources().getString(R.string.strTeam));
                    String id = player.optString(getResources().getString(R.string.idPlayer));
                    String description = player.optString(getResources().getString(R.string.strDescriptionEN));
                    String nationality = player.optString(getResources().getString(R.string.strNationality));
                    String image = player.optString(getResources().getString(R.string.strThumb));
                    JsonPlayer jsonPlayer = new JsonPlayer(id, name, teamname, image, nationality, description);
                    jsonPlayers.add(jsonPlayer);
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
            playerAdapter = new PlayerAdapter(PlayerActivity.this, jsonPlayers);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(playerAdapter);
            recyclerView.scrollToPosition(scroll);
        }
    }

}
