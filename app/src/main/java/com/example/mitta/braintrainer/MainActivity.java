package com.example.mitta.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int correctAnswer;
    private int correctButton;
    private int correctAnswersSum;
    private int totalAnswersSum;

    private String gameState = "Ready";

    private TextView quizTextView;
    private TextView resultsView;
    private TextView answerView;
    private TextView timerView ;

    private Button goButton;

    // Starts the game when the "GO" button is pressed
    public void startGame(View view){

        if(gameState.equals("Finished")){
            restartGame();
        }
        else if(gameState.equals("Ready")) {

            gameState = "Running";
            answerView.setText("GO!");
            nextQuiz();
            goButton.setVisibility(View.INVISIBLE);

            CountDownTimer timer = new CountDownTimer(30000 + 100, 1000) {
                @Override
                public void onTick(long millisecondsUntilDone) {

                    int secondsLeft = (int) millisecondsUntilDone / 1000;
                    updateTimer(secondsLeft);

                }

                @Override
                public void onFinish() {

                    updateTimer(0);
                    goButton.setText("Restart");
                    goButton.setVisibility(View.VISIBLE);
                    String endOfGameString = "Game Over! " + "Your score: " + correctAnswersSum + "/" + totalAnswersSum;
                    answerView.setText(endOfGameString);
                    gameState = "Finished";

                }

            }.start();
        }
    }

    // Resets scores and UI to the defaults
    public void restartGame(){

        timerView.setText("00:30");
        quizTextView.setText("");
        resultsView.setText("0/0");
        correctAnswersSum = 0;
        totalAnswersSum = 0;
        gameState = "Ready";
        answerView.setText("Get Ready");
        goButton.setText("GO");

    }
    // Updates the timer on the top left of the screen
    public void updateTimer(int secondsLeft){

        int minutes = secondsLeft / 60;
        int seconds = secondsLeft % 60;
        // Avoid appearance of seconds with 1 digit , e.g 10:0 instead of 10:00
        String secondsString = Integer.toString(seconds);
        if(seconds < 10){
            secondsString = "0" + secondsString;
        }

        String time = minutes + ":" + secondsString;
        timerView.setText(time);

    }

    // Generates a random number between 0 and the int specified
    public int generateRandomNumber(int limit){

        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(limit);
        return randomNumber;

    }

    // Sets up the next quiz
    public void nextQuiz(){

        // Generate the quiz and answer
        int firstNum = generateRandomNumber(100);
        int secondNum = generateRandomNumber(100);
        correctAnswer = firstNum + secondNum;
        correctButton = generateRandomNumber(4);

        //Log.i("Random checker:", "First:" + firstNum + " Second:" + secondNum + " correctAnswer: " + correctAnswer + " Correct Button: " + correctButton);

        // Set up the answers on the buttons
        Button[] buttons = new Button[4];
        buttons[0] = findViewById(R.id.button1);
        buttons[1] = findViewById(R.id.button2);
        buttons[2] = findViewById(R.id.button3);
        buttons[3] = findViewById(R.id.button4);

        Log.i("Check","Correct Answer: " + correctAnswer + "\nCorrect Button: " + correctButton);

        for(int buttonIndex = 0; buttonIndex < 4 ;buttonIndex++){

            if(buttonIndex != correctButton){
                int randomNumber = generateRandomNumber(150);
                buttons[buttonIndex].setText(String.valueOf(randomNumber));
            }
            else if(buttonIndex == correctButton){
                buttons[buttonIndex].setText(String.valueOf(correctAnswer));
            }

        }

        // Set up the quiz indicator
        String quizString = firstNum + " + " + secondNum + "?";
        quizTextView.setText(quizString);

        // Set up the results indicator
        String resultsString = correctAnswersSum + "/" + totalAnswersSum;
        resultsView.setText(resultsString);


    }

    // Evaluates the answer when one of the answer buttons is pressed, prompts the next quiz
    public void onAnswerClick(View view){

        if(gameState.equals("Running")) {

            int buttonTag = Integer.parseInt(view.getTag().toString());

            if (buttonTag == correctButton) {

                correctAnswersSum++;
                answerView.setText("Correct!");

            } else {

                answerView.setText("Wrong!");

            }

            totalAnswersSum++;
            nextQuiz();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerView = findViewById(R.id.timerTextView);
        answerView = findViewById(R.id.answerTextView);
        quizTextView = findViewById(R.id.quizTextView);
        resultsView = findViewById(R.id.resultsTextView);
        goButton = findViewById(R.id.goButton);
    }

}
