package com.example.progress.TaskList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.progress.MainActivity;
import com.example.progress.Models.CheckList;
import com.example.progress.Models.Task;
import com.example.progress.R;
import com.parse.ParseException;

import com.parse.SaveCallback;

public class CreateTaskActivity extends AppCompatActivity {
    private static final String TAG = "Create Task";
    private EditText etCreateTaskName;
    private EditText etCreateTaskDescription;
    private Button btnSubmitTask;
    private Context context;
    private CheckList checklist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        btnSubmitTask = findViewById(R.id.btnSubmitTask);
        etCreateTaskName = findViewById(R.id.etCreateTaskName);
        etCreateTaskDescription = findViewById(R.id.etCreateTaskDescription);
        context = this;
        checklist = getIntent().getParcelableExtra("checklist");
        btnSubmitTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etCreateTaskName.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(context, "Please enter a name", Toast.LENGTH_SHORT).show();
                    return;
                }
                createNewTask(name,etCreateTaskDescription.getText().toString());

            }
        });
    }

    public void createNewTask(String taskName, String taskDescription){
        Task task = new Task();
        task.setDescription(taskDescription);
        task.setName(taskName);
        task.setChecklist(checklist);
        task.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Log.e(TAG,"Error while creating task");
                    Toast.makeText(context,"Error while making task", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Task was created successfully");
                etCreateTaskName.setText("");
                etCreateTaskDescription.setText("");
                goToMain();
            }

        });
    }
    private void goToMain() {
        Intent i = new Intent(CreateTaskActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}