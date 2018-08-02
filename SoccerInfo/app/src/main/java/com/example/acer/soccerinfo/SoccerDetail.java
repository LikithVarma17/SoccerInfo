package com.example.acer.soccerinfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;

public class SoccerDetail extends AppCompatActivity {
    CardView tv, tv1, tv2;
    FirebaseAuth auth;
    ImageView teamIcon, scheduleIcon, resultIcon;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            if (checkOnline()) {
                SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.PlayerPref), 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Intent intent = new Intent(SoccerDetail.this, MainActivity.class);
                editor.putBoolean(getResources().getString(R.string.Logged), false);
                editor.apply();
                finish();
                startActivity(intent);
            } else {
                Toast.makeText(this, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soccer_detail);
        tv = (CardView) findViewById(R.id.teams);
        tv1 = (CardView) findViewById(R.id.scedule);
        tv2 = (CardView) findViewById(R.id.result);
        teamIcon = (ImageView) findViewById(R.id.team_icon);
        scheduleIcon = (ImageView) findViewById(R.id.schedule_icon);
        resultIcon = (ImageView) findViewById(R.id.result_icon);
        teamIcon.setImageResource(R.drawable.team);
        scheduleIcon.setImageResource(R.drawable.versus);
        resultIcon.setImageResource(R.drawable.result);
        auth = FirebaseAuth.getInstance();
        AdView mAdView;
        mAdView = findViewById(R.id.banner);
        MobileAds.initialize(this, "ca-app-pub-6336523906622902~6817819328");
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("C3528C0925D3AB8424433F9070BD2A5F")
                .build();
        mAdView.loadAd(adRequest);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SoccerDetail.this, Teams.class);
                startActivity(intent);
            }

        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SoccerDetail.this, Schedule.class);
                startActivity(intent);
            }

        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SoccerDetail.this, Result.class);
                startActivity(intent);
            }

        });

    }

    public boolean checkOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null)
            return true;
        else
            return false;
    }

}
