package com.example.progress.TaskList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.progress.Alarms.AlarmMainActivity;
import com.example.progress.MainActivity;
import com.example.progress.Models.CheckList;
import com.example.progress.R;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class ChecklistSettingsActivity extends AppCompatActivity {

    private static final String TAG = "Checklist Settings Activity";
    private Switch swHasNotifs;
    private Switch swHasQuiz;
    private Switch swHasAlbum;
    private Button btnDeleteList, btnSubmit, btnToCalendar;
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
        btnToCalendar = findViewById(R.id.btnToCalendar);

        etChecklistSettingsDescription = findViewById(R.id.etTaskSettingsDescription);
        etChecklistSettingsTitle = findViewById(R.id.etTaskSettingsTitle);

        btnToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChecklistSettingsActivity.this, AlarmMainActivity.class);

                startActivity(i);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Log.i(TAG, "Saving Data for hasNotifs\n Current Boolean: " + checklist.getHasNotifs() + "\nChanged to: " + swHasNotifs.isChecked());
                    checklist.setHasNotifs(swHasNotifs.isChecked());

                    Log.i(TAG, "Saving Data for hasPhotos\n Current Boolean: " + checklist.getHasPhotos() + "\nChanged to: " + swHasAlbum.isChecked());
                    checklist.setHasPhotos(swHasAlbum.isChecked());

                    Log.i(TAG, "Saving Data for hasQuiz\n Current Boolean: " + checklist.getHasQuiz() + "\nChanged to: " + swHasQuiz.isChecked());
                    checklist.setHasQuiz(swHasQuiz.isChecked());

                    Log.i(TAG, "Saving Data for checklist name\n Current Name: " + checklist.getName() + "\nChanged to: " + etChecklistSettingsTitle.getText().toString());
                    checklist.setName(etChecklistSettingsTitle.getText().toString());

                    Log.i(TAG, "Saving Data for checklist desc\n Current Desc: " + checklist.getDescription() + "\nChanged to: " + etChecklistSettingsDescription.getText().toString());
                    checklist.setDescription(etChecklistSettingsDescription.getText().toString());

                    checklist.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                Log.e(TAG, "Error while saving checklist data: " + checklist.getName());
                            }
                        }
                    });
                    Intent i = new Intent(ChecklistSettingsActivity.this, MainActivity.class);
                    startActivity(i);

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