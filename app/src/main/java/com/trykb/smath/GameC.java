package com.trykb.smath;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;
import java.util.Random;

public class GameC extends AppCompatActivity {
    TextView score;
    TextView life;
    TextView time;
    TextView question;
    EditText answer;
    Button ok;
    Button nextquestion;

    //generating the Random class and its defined boundary
    Random rand = new Random();
    int num1;
    int num2;
    int userAnswer;
    int correctAnswer;
    int userScore = 0;
    int userLife = 3;

    CountDownTimer timer;
    private static final long START_TIMER_IN_MILIS = 40000;
    Boolean timer_run;
    long time_left_in_milis = START_TIMER_IN_MILIS;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_c);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        score = findViewById(R.id.textViewScore);
        life = findViewById(R.id.textViewLife);
        time = findViewById(R.id.textViewTime);
        question = findViewById(R.id.textViewQuestion);
        answer = findViewById(R.id.editTextAnswer);
        ok = findViewById(R.id.buttonok);
        nextquestion = findViewById(R.id.buttonnext);

        Continuegame();


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userAnswer = Integer.valueOf(answer.getText().toString());

                pauseTimer();

                if (userAnswer == correctAnswer){


                    userScore = userScore + 5;
                    score.setText("" +userScore);
                    question.setText("Amazing, You are correct!");
                }
                else
                {

                    userLife -= 2;
                    life.setText("" +userLife);
                    question.setText("Sorry,answer is incorrect");

                }

            }
        });

        nextquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              answer.setText("");
                Continuegame();
                resetTimer();


            }
        });


    }

   public void Continuegame()
   {
       num1 = rand.nextInt(100);
       num2 = rand.nextInt(100);

       correctAnswer = num1 + num2;

       question.setText(num1 + "+" + num2);
       startTimer();

   }

  public void startTimer()
  {
        timer = new CountDownTimer(time_left_in_milis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                time_left_in_milis=millisUntilFinished;
                updateTimer();

            }

            @Override
            public void onFinish() {
                timer_run = false;
                pauseTimer;
                resetTimer;
                updateText();

                userLife -= 1;
                life.setText(""+userLife);
                question.setText("Sorry, time is up!");


            }
        }.start();

        timer_run = true;
   }

   public void updateText()
   {
       int second = (int)(time_left_in_milis/1000) % 40;
       String time_left = String.format(Locale.getDefault(),"%02d",second);
       time.setText(time_left);

   }

   public void pauseTimer()
   {
     timer.cancel();
     timer_run = false;
   }

   public void resetTimer(){

        time_left_in_milis = START_TIMER_IN_MILIS;
        updateText();
   }
}