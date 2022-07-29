package com.example.progress;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.facebook.ParseFacebookUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collection;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button ibConfirm;
    private Button btnFacebook;
    private TextView tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(ParseUser.getCurrentUser() != null)  {
            goMainActivity();
        }
        etUsername = findViewById(R.id.etUsername );
        etPassword = findViewById(R.id.etPassword);
        ibConfirm = findViewById(R.id.btnConfirm);
        tvSignup = findViewById(R.id.tvSignup);
        btnFacebook = findViewById(R.id.btnFacebook);

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

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
                dialog.setTitle("Please, wait a moment.");
                dialog.setMessage("Logging in...");
                dialog.show();
                Collection<String> permissions = Arrays.asList("public_profile","email");
                ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginActivity.this, permissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e != null) {
                            ParseUser.logOut();
                            Log.e("error", "Error", e);
                        }
                        if (user == null) {
                            ParseUser.logOut();
                            Toast.makeText(LoginActivity.this, "The user cancelled the Facebook Login", Toast.LENGTH_SHORT).show();
                        } else if (user.isNew()) {
                            Toast.makeText(LoginActivity.this, "User signed up and Logged in through Facebook", Toast.LENGTH_SHORT).show();
                            getUserDetailFromFB();
                        } else {
                            Toast.makeText(LoginActivity.this, "User logged in through Facebook", Toast.LENGTH_SHORT).show();
                            getUserDetailFromParse();
                        }
                    }
                });
            }
        });
    }

    private void getUserDetailFromFB() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                ParseUser user = ParseUser.getCurrentUser();
                try {
                    user.setUsername(object.getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    user.setEmail(object.getString("email"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        alertDisplayer("First time Login","Welcome!");
                    }
                });
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields","name,email");
        request.setParameters(parameters);
        request.executeAsync();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode,resultCode,data);
    }



    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

    private void getUserDetailFromParse() {
        ParseUser user = ParseUser.getCurrentUser();
        String title = "Welcome Back";
        String message = "User: " + user.getUsername() + "\n" + "Login Email: " + user.getEmail();
        alertDisplayer(title,message);
    }

    private void alertDisplayer(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

    private void loginUser(String user, String pass) {

        Log.i(TAG, "Attempting to LoginActivity: " +user + " " + pass);
        ParseUser.logInInBackground(user, pass, new LogInCallback(){
            @Override
            public void done(ParseUser user, ParseException e) {
            if(e != null){
                Log.e(TAG, "Issue with logic: ",e);
                return;
            }
            goMainActivity();
            Toast.makeText(LoginActivity.this, "Success!!", Toast.LENGTH_LONG).show();

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