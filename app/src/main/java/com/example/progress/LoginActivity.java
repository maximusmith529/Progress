package com.example.progress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button ibConfirm;
    private TextView tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        if(ParseUser.getCurrentUser() != null)  {
//            goMainActivity();
//        }
        etUsername = findViewById(R.id.etUsername );
        etPassword = findViewById(R.id.etPassword);
        ibConfirm = findViewById(R.id.btnConfirm);
        tvSignup = findViewById(R.id.tvSignup);

        ibConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "confirm LoginActivity clicked");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                loginUser(username, password);
            }


        });
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Signup clicked");
                goSignup();

            }
        });
    }
    private void loginUser(String user, String pass) {

        Log.i(TAG, "Attempting to LoginActivity: " +user + " " + pass);
        ParseUser.logInInBackground(user, pass, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with logic: ",e);
                    return;
                }
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Success!!", Toast.LENGTH_LONG);

            }
        });
    }

    private void goSignup(){
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
        finish();
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();

    }


}