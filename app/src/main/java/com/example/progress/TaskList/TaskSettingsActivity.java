package com.example.progress.TaskList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.progress.Models.Task;
import com.example.progress.R;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class TaskSettingsActivity extends Activity {

    public static final String TAG = "Task Settings Activity";
    private EditText etDetailTaskName,etDetailTaskDescription;
    private TextView tvDetailTaskTitle;
    private Task task;
    private Button btnDetailTaskSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_settings);
        task = getIntent().getParcelableExtra("task");
        tvDetailTaskTitle = findViewById(R.id.tvDeleteTaskTitle);
        etDetailTaskName = findViewById(R.id.etDetailTaskName);
        etDetailTaskDescription = findViewById(R.id.etDetailTaskDescription);

        btnDetailTaskSubmit = findViewById(R.id.btnDeleteTaskConfirm);

        etDetailTaskDescription.setText(task.getDescription());
        etDetailTaskName.setText(task.getName());

        btnDetailTaskSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Detail Submit Clicked");
                task.setName(etDetailTaskName.getText().toString());
                task.setDescription(etDetailTaskDescription.getText().toString());
                task.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        finish();
                    }
                });
            }
        });
    }


}