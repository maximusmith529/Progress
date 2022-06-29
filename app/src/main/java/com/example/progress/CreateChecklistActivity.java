package com.example.progress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.progress.Models.CheckList;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class CreateChecklistActivity extends AppCompatActivity {

    private static final String TAG = "Create Checklist";
    private EditText etCreateChecklistName;
    private EditText etCreateChecklistDescription;
    private Button btnSubmitChecklist;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_checklist);

        btnSubmitChecklist = findViewById(R.id.btnSubmitChecklist);
        etCreateChecklistName = findViewById(R.id.etCreateChecklistName);
        etCreateChecklistDescription = findViewById(R.id.etCreateChecklistDescription);
        context = this;
        btnSubmitChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etCreateChecklistName.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(context, "Please enter a name", Toast.LENGTH_SHORT).show();
                    return;
                }
                createNewChecklist(name,etCreateChecklistDescription.getText().toString());

            }
        });
    }

    public void createNewChecklist(String checklistName, String checklistDescription){
        CheckList checklist = new CheckList();
        checklist.setDescription(checklistDescription);
        checklist.setName(checklistName);
        checklist.setUser(ParseUser.getCurrentUser());
        checklist.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Log.e(TAG,"Error while creating checklist");
                    Toast.makeText(context,"Error while making checklist", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Checklist was created successfully");
                etCreateChecklistName.setText("");
                etCreateChecklistDescription.setText("");
            }
        });


    }
}