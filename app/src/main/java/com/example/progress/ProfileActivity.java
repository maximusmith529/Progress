package com.example.progress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvUsername;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.tbProfileBar);
        setSupportActionBar(toolbar);
        tvUsername = findViewById(R.id.tvUsername);
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { logout(v); }
        });
    }



    public void logout(View view) {

        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();
        Log.i("User", "User: "+ParseUser.getCurrentUser());
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}