package com.example.progress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.progress.Fragments.CheckListsFragment;
import com.example.progress.Fragments.ReflectionFragment;
import com.example.progress.Fragments.TaskListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private ImageView btnToProfile;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.progress",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (PackageManager.NameNotFoundException e) {
        }
        catch (NoSuchAlgorithmException e) {
        }
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnToProfile = (ImageView)findViewById(R.id.btnAddPhotoToAlbum);
        bottomNavigationView = findViewById(R.id.bottom_navigation);


        btnToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfile(v);
            }
        });




        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.btnCheckListsView:
                        Toast.makeText(MainActivity.this, "Check Lists", Toast.LENGTH_SHORT).show();
                        fragment = new CheckListsFragment();
                        break;
                    case R.id.btnTaskListView:
                        Toast.makeText(MainActivity.this, "Task List", Toast.LENGTH_SHORT).show();
                        fragment = new TaskListFragment();
                        break;
                    case R.id.btnReflectionView:
                    default:
                        Toast.makeText(MainActivity.this, "Reflection", Toast.LENGTH_SHORT).show();
                        fragment = new ReflectionFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        if(getIntent().hasExtra("fragment"))
            bottomNavigationView.setSelectedItemId(getIntent().getIntExtra("fragment",R.id.btnTaskListView));
        else
            bottomNavigationView.setSelectedItemId(R.id.btnTaskListView);

    }



    public void goToProfile(View view){
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }
}