package com.example.progress;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
                                //Might need to change the answer array
                                setScreen(R.string.outsideForcesResult, R.array.toTaskListAnswers);
                                break;
                            case "Yes, the tasks were too hard":
                                setScreen(R.string.tasksTooHardResult, R.array.toTaskListAnswers);
                                break;
                            case "Yes, there were too many tasks":
                                setScreen(R.string.tooManyTasksResult, R.array.toTaskListAnswers);
                                break;
                            case "No":

                                break;
                        }
                        break;

                    case "It helps to have a good environment around you while you work as it can help boost your productivity and reduce stress":
                    //TODO: Setup Popup Message

                        break;

                    case("Is it ok that they were easy?"):
                        switch(answer) {
                            case "Yes":
                                goToReflection(getString(R.string.tasksWereGoodEasyResult));
                                break;
                            case "No, I\'d prefer it to be tougher":
                                 setScreen(R.string.tasksTooEasyResult, R.array.toTaskListAnswers);
                                break;
                        }
                        break;

                    case "It\'s important for us to set realistic goals so that we may attain them and grow.\nWould you like to edit your tasks for next time?":
                        switch(answer){
                            case "Let\'s go":
                                goToTaskList("Remember that the routines are meant for self growth. Everyone has their own pace. \nPlease try to remove or edit a few tasks to make your workload more achievable");
                                break;
                            case "Maybe later":
                                goToReflection(null);
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

    void goToReflection(@Nullable String message){
        if(message == null)
        {
            Intent i = new Intent(QuizActivity.this, MainActivity.class);
            i.putExtra("fragment", R.id.btnReflectionView);
            startActivity(i);
        }
        finish();
    }

    void goToTaskList(String message){

    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.overflow_menu, menu);
//        return true;
//    }
}