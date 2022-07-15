package com.example.progress.TaskList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.progress.Models.CheckList;
import com.example.progress.R;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class ChecklistSettingsActivity extends AppCompatActivity {

    private static final String TAG = "Checklist Settings Activity";
    private Switch swHasNotifs;
    private Switch swHasQuiz;
    private Switch swHasAlbum;
    private Button btnDeleteList;
    private Button btnSubmit;
    private EditText etChecklistSettingsTitle;
    private EditText etChecklistSettingsDescription;
    private CheckList checklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_settings);
        checklist = getIntent().getParcelableExtra("checklist");

        swHasAlbum = findViewById(R.id.swHasAlbum);
        swHasNotifs = findViewById(R.id.swHasNotifs);
        swHasQuiz = findViewById(R.id.swHasQuiz);

        btnDeleteList = findViewById(R.id.btnDeleteList);
        btnSubmit = findViewById(R.id.btnSubmitChecklistSettings);

        etChecklistSettingsDescription = findViewById(R.id.etChecklistSettingsDescription);
        etChecklistSettingsTitle = findViewById(R.id.etChecklistSettingsTitle);

        swHasAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "swHasAlbum was clicked");
                checklist.setHasPhotos(!checklist.getHasPhotos());
            }
        });
        swHasNotifs.setOnClickListener(new View.OnClickListener() {
            //TODO: change this to check and add during the submit process
           @Override
           public void onClick(View v) {
               Log.i(TAG, "swHasNotifs was clicked\n Current Boolean: " + checklist.getHasNotifs() + "\n!Version: " + !checklist.getHasNotifs());
               checklist.setHasNotifs(!checklist.getHasNotifs());
               checklist.saveInBackground(new SaveCallback() {
                   @Override
                   public void done(ParseException e) {
                       if (e != null) {
                           Log.e(TAG, "Error while creating checklist");
                       }
                   }
               });
           }
       });

        swHasQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "swHasQuiz was clicked");
                checklist.setHasQuiz(!checklist.getHasQuiz());
            }
        });

        checkSwitches();
        setText();
    }

    private void setText() {
        etChecklistSettingsTitle.setText(checklist.getName());
        if(checklist.getDescription() != null)
            etChecklistSettingsDescription.setText(checklist.getDescription());
    }

    private void checkSwitches(){
        if(checklist.getHasPhotos())
            swHasAlbum.setChecked(true);
        if(checklist.getHasQuiz())
            swHasQuiz.setChecked(true);
        if(checklist.getHasNotifs())
            swHasNotifs.setChecked(true);
    }

}