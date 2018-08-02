package com.example.acer.soccerinfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText ed, ed1;
    Button button;
    Button button1;
    Context context;
    FirebaseAuth auth;
    String email;
    String password;
    Snackbar snackbar;
    LinearLayout linearLayout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.PlayerPref), 0);
        editor = sharedPreferences.edit();
        button = (Button) findViewById(R.id.login);
        button1 = (Button) findViewById(R.id.register);
        ed = (EditText) findViewById(R.id.id_user);
        ed1 = (EditText) findViewById(R.id.id_password);
        auth = FirebaseAuth.getInstance();
        if (sharedPreferences.getBoolean(getResources().getString(R.string.Logged), false)) {
            Intent intent = new Intent(MainActivity.this, SoccerDetail.class);
            finish();
            startActivity(intent);
        }
        linearLayout = (LinearLayout) findViewById(R.id.id_linear);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = ed.getText().toString();
                password = ed1.getText().toString();
                if (email.isEmpty()) {
                    ed.setError(getString(R.string.Email_required));
                } else {
                    if (password.isEmpty()) {
                        ed1.setError(getString(R.string.password_required));
                    } else {
                        auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        if (!task.isSuccessful()) {
                                            // there was an error
                                            if (password.length() < 6) {
                                                ed1.setError(getString(R.string.minimum_password));
                                            } else {

                                                snackbar = Snackbar.make(linearLayout, getResources().getString(R.string.auth_failed), Snackbar.LENGTH_SHORT);
                                                snackbar.show();

                                            }
                                        } else {
                                            editor.putBoolean(getResources().getString(R.string.Logged), true);
                                            editor.apply();
                                            Intent intent = new Intent(MainActivity.this, SoccerDetail.class);
                                            finish();
                                            startActivity(intent);
                                        }
                                    }
                                });

                    }
                }

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = ed.getText().toString();
                password = ed1.getText().toString();
                if (email.isEmpty()) {
                    ed.setError(getResources().getString(R.string.Email_required));
                } else {
                    if (password.isEmpty()) {
                        ed1.setError(getResources().getString(R.string.password_required));
                    } else {
                        auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Toast.makeText(MainActivity.this, getResources().getString(R.string.Success) + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        if (!task.isSuccessful()) {
                                            snackbar = Snackbar.make(linearLayout, getResources().getString(R.string.auth_failed), Snackbar.LENGTH_SHORT);
                                            snackbar.show();
                                        } else {
                                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                                            finish();
                                        }
                                    }
                                });
                    }
                }


            }
        });

    }

}
