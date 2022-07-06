package com.example.progress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.progress.Models.CheckList;

public class AlbumActivity extends AppCompatActivity {

    private CheckList checklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        checklist = getIntent().getParcelableExtra("checklist");
    }

}