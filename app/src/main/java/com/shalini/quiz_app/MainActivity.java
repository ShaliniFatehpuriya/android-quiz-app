package com.shalini.quiz_app;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView totalQueHeading;
    TextView questionOne;
    Button ansA,ansB,ansC,ansD;
    Button submitButton;
    LinearLayout answersLayout;

    int score;
    int totalQuestions = Questions.question.length;
    int curentQuestion = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //linking the Java elements to the XML elements
        totalQueHeading = findViewById(R.id.totalQueHeading);
        questionOne = findViewById(R.id.questionOne);
        submitButton = findViewById(R.id.submitButton);
        ansA = findViewById(R.id.ansA);
        ansB = findViewById(R.id.ansB);
        ansC = findViewById(R.id.ansC);
        ansD = findViewById(R.id.ansD);


        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitButton.setOnClickListener(this);

        totalQueHeading.setText("Total Questions are" +totalQuestions);
        loadQuestion();

    }

    private void loadQuestion(){
        if(curentQuestion == totalQuestions){
            finishQuiz();
            //VERY VERY IMPORTANT the return statement ensures that any code below this wont execute after finishQuiz is done executing.
            // preventing a potential crash or unexpected behaviour.
            return;
        }
        questionOne.setText(Questions.question[curentQuestion]);
        ansA.setText(Questions.choices[curentQuestion][0]);
        ansB.setText(Questions.choices[curentQuestion][1]);
        ansC.setText(Questions.choices[curentQuestion][2]);
        ansD.setText(Questions.choices[curentQuestion][3]);

        selectedAnswer="";
    }
    protected void finishQuiz(){
        String passedStatus;
        if(score>=totalQuestions*0.6){
            passedStatus="passed";

        }else{
            passedStatus="failed";

        }
        new AlertDialog.Builder(this)
                .setTitle(passedStatus)
                .setMessage("You have "+passedStatus+" with score "+score)
                .setPositiveButton("Restart",((dialog,i)->{
                    restartQuiz();
                    dialog.dismiss();
                    loadQuestion();
                }))
                .show();
    }
    protected void  restartQuiz(){
        score=0;
        curentQuestion=0;
        recreate();
    }

    @Override
    public void onClick(View view) {
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        Button buttonclicked = (Button) view;
        if(buttonclicked.getId()==R.id.submitButton){
            if(!selectedAnswer.isEmpty()){
                if(selectedAnswer.equals(Questions.answers[curentQuestion])) {
                    score++;
                }else {
                    buttonclicked.setBackgroundColor(Color.MAGENTA);
                }
                curentQuestion++;
                loadQuestion();
            }else{
                Toast.makeText(MainActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
            }
        }else{
            selectedAnswer=buttonclicked.getText().toString();
            buttonclicked.setBackgroundColor(Color.YELLOW);
        }
    }
}