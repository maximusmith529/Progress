package com.example.progress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.progress.Models.CheckList;

import java.lang.reflect.Array;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "Quiz Activity";
    private Spinner spAnswers;
    private ArrayAdapter<CharSequence> adapter;
    private TextView tvQuestion, tvQuizTitle;
    private CheckList checkList;
    private Button btnQuizSubmit;
    private Context context;
    private String question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        spAnswers = findViewById(R.id.spAnswers);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvQuizTitle = findViewById(R.id.tvQuizTitle);
        btnQuizSubmit = findViewById(R.id.btnQuizSubmit);
        context = this;
        question = getString(R.string.difficultyQuestion);
        adapter = ArrayAdapter.createFromResource(context, R.array.difficultyAnswers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spAnswers.setAdapter(adapter);

        checkList = getIntent().getParcelableExtra("checklist");
        tvQuizTitle.setText(checkList.getName());
        tvQuestion.setText(R.string.difficultyQuestion);
        btnQuizSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = spAnswers.getSelectedItem().toString();
                question = tvQuestion.getText().toString();
                int i = 0;
                Log.i(TAG, "Selected item: "+answer.toString()+" Submitted");
                switch(question){
                    case "How difficult were today\'s activities in this list?":
                        switch(answer) {
                        case "Very Easy":


                        case "Easy":
                            Log.i(TAG, "Easy Mode Activated");
                            setScreen(R.string.difficultyQuestionEasy, R.array.difficultyAnswersEasy);
                            break;
                        case "Perfect":

                            break;
                        case "Challenging":


                        case "Difficult":
                            Log.i(TAG, "Hard Mode Activated");
                            setScreen(R.string.difficultyQuestionHard, R.array.difficultyAnswersHard);
                            break;
                    }
                    break;

                    case "Was it too difficult and if it was why?":
                        switch(answer) {
                            case "Yes, outside forces":
                                setScreen(R.string.outsideForcesResult, R.array.toTaskListAnswers);
                                break;
                            case "Yes, the tasks were too hard":
                                setScreen(R.string.tasksTooHardResult, R.array.toTaskListAnswers);
                                break;
                            case "Yes, there were too many tasks":

                                break;
                            case "No":

                                break;
                        }
                        break;

                        case "It helps to have a good environment around you while you work as it can help boost your productivity and reduce stress"
                    case("Was it too easy?"):
                        switch(answer) {
                            case "Yes":

                                break;
                            case "No, I\'d prefer it to be tougher":

                                break;
                        }
                        break;

                }
            }
        });


    }

    private void setScreen(int questionID, int answersID){
        tvQuestion.setText(questionID);
        adapter = ArrayAdapter.createFromResource(context, answersID, android.R.layout.simple_spinner_item);
        spAnswers.setAdapter(adapter);
    }

    void goToTaskList(String message){

    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.overflow_menu, menu);
//        return true;
//    }
}