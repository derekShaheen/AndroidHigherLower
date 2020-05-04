package com.shaheen.higherlower;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Derek Shaheen
 * @date: Apr 17, 2020
 * @version: 1.0
 */

public class MainActivity extends AppCompatActivity {

    ImageButton buttonHigher;
    ImageButton buttonLower;
    TextView tvCurrentNumber;
    TextView tvCurrentScore;
    TextView tvCurrentRound;
    TextView tvCurrentSessionHighScore;
    TextView tvHighScore;
    TextView tvIndicator;

    int currentRound = 1;
    int sessionHighScore = 0;
    HigherLowerModel theModel;
    DatabaseManager dbMgr;
    private int choiceResult = -99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theModel = new HigherLowerModel();
        dbMgr = new DatabaseManager(this);

        setupCallButtonClickEvents();

        tvCurrentNumber = findViewById(R.id.tvCurrentNumber);
        tvCurrentRound = findViewById(R.id.tvRoundNumber);
        tvCurrentScore = findViewById(R.id.tvCurrentScore);
        tvIndicator = findViewById(R.id.tvIndicator);
        tvHighScore = findViewById(R.id.tvHighScore);
        tvCurrentSessionHighScore = findViewById(R.id.tvSessionHighScore);

        tvHighScore.setText(dbMgr.dbGetHighScore() + "");
        tvCurrentNumber.setText(theModel.getString());
        tvIndicator.setText("");
    }

    private void setupCallButtonClickEvents() {
        buttonHigher = findViewById(R.id.btnHigher);
        buttonHigher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                theModel.gen();
                choiceResult = theModel.checkChoice(1); // Send higher parameter
                setTextboxes();
                checkHighscore();
            }
        });

        buttonLower = findViewById(R.id.btnLower);
        buttonLower.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                theModel.gen();
                choiceResult = theModel.checkChoice(0); // Send lower parameter
                setTextboxes();
                checkHighscore();
            }
        });
    }

    private void setTextboxes() {
        if (choiceResult > 0) {
            tvIndicator.setText("Correct!");
            tvCurrentScore.setTextColor(getResources().getColor(R.color.gameCorrect));
        } else {
            tvIndicator.setText("Incorrect!");
            tvCurrentScore.setTextColor(getResources().getColor(R.color.gameIncorrect));
        }
        tvCurrentScore.setText(theModel.getScore());
        tvCurrentNumber.setText(theModel.getString());
        tvHighScore.setText(dbMgr.dbGetHighScore() + "");

        currentRound++;
        tvCurrentRound.setText(currentRound + "");
    }

    private void checkHighscore() {
        if (Integer.parseInt(theModel.getScore()) > dbMgr.dbGetHighScore()) {
            dbMgr.dbInsertNewScore(theModel.getScore(), currentRound + "");
            tvHighScore.setText(dbMgr.dbGetHighScore() + "");
        }
        if (Integer.parseInt(theModel.getScore()) > sessionHighScore) {
            sessionHighScore = Integer.parseInt(theModel.getScore());
            tvCurrentSessionHighScore.setText(sessionHighScore + "");
        }
    }

}
