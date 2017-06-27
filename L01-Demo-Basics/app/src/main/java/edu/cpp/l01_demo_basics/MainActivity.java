package edu.cpp.l01_demo_basics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.cpp.l01_demo_basics.data.QuizQuestion;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;

    private List<QuizQuestion> questions;
    private int questionIndex;

    private void initQuestions() {
        questions = new ArrayList<QuizQuestion>() {{
            add(new QuizQuestion("NYC is the largest city in the US", true));
            add(new QuizQuestion("CA is the largest state in the US", false));
            add(new QuizQuestion("CS499 is the best course in the CS", true));
            add(new QuizQuestion("CS has the largest number of students in the college of science.", false));
        }};
        questionIndex = 0;
    }

    private void updateQuestion() {
        if (questionIndex >= questions.size()) {
            questionIndex = 0;
        }
        QuizQuestion currentQuestion = questions.get(questionIndex);
        questionTextView.setText(currentQuestion.getDescription());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("TEST", "onCreate");

        setContentView(R.layout.activity_quiz);
        questionTextView = (TextView) findViewById(R.id.questionTextView);

        initQuestions();
        updateQuestion();

        Button trueButton = (Button) findViewById(R.id.trueButton);
        trueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (questions.get(questionIndex).getCorrectAnswer() == true) {
                    Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button falseButton = (Button) findViewById(R.id.falseButton);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questions.get(questionIndex).getCorrectAnswer() == false) {
                    Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionIndex++;
                updateQuestion();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("TEST", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TEST", "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("TEST", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TEST", "onDestroy");
    }
}

