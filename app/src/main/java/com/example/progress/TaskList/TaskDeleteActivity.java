package com.example.progress.TaskList;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.progress.Models.Task;
import com.example.progress.R;
import com.parse.DeleteCallback;
import com.parse.ParseException;

public class TaskDeleteActivity extends Activity {

    private Button btnDeleteTaskCancel, btnDeleteTaskConfirm;
    private Task task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_delete);

        btnDeleteTaskCancel = findViewById(R.id.btnDeleteTaskCancel);
        btnDeleteTaskConfirm = findViewById(R.id.btnDeleteTaskConfirm);
        task = getIntent().getParcelableExtra("task");
        btnDeleteTaskConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.deleteInBackground(new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        finish();
                    }
                });
            }
        });
        btnDeleteTaskCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}