package com.shaheen.higherlower;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

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

    private int choiceResult = -99;

    HigherLowerModel theModel;
    DatabaseManager dbMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theModel = new HigherLowerModel();
        dbMgr = new DatabaseManager(this);

        setupCallButtonClickEvents();

        tvCurrentNumber = (TextView) findViewById(R.id.tvCurrentNumber);
        tvCurrentRound = (TextView) findViewById(R.id.tvRoundNumber);
        tvCurrentScore = (TextView) findViewById(R.id.tvCurrentScore);
        tvIndicator = (TextView) findViewById(R.id.tvIndicator);
        tvHighScore = (TextView) findViewById(R.id.tvHighScore);
        tvCurrentSessionHighScore = (TextView) findViewById(R.id.tvSessionHighScore);

        tvHighScore.setText(dbMgr.dbGetHighScore() + "");
        tvCurrentNumber.setText(theModel.getString());
        tvIndicator.setText("");
    }

    private void setupCallButtonClickEvents() {
        buttonHigher = (ImageButton) findViewById(R.id.btnHigher);
        buttonHigher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                theModel.gen();
                choiceResult = theModel.checkChoice(1);
                setTextboxes();
                checkHighscore();
            }
        });

        buttonLower = (ImageButton) findViewById(R.id.btnLower);
        buttonLower.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                theModel.gen();
                choiceResult = theModel.checkChoice(0);
                setTextboxes();
                checkHighscore();
            }
        });
    }

    private void setTextboxes(){
        if(choiceResult > 0) {
            tvIndicator.setText("Correct!");
            tvCurrentScore.setTextColor(getResources().getColor(R.color.gameCorrect));
        }
        else {
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
        if(Integer.parseInt(theModel.getScore()) > dbMgr.dbGetHighScore()) {
            dbMgr.dbInsertNewScore(theModel.getScore(), currentRound + "");
            tvHighScore.setText(dbMgr.dbGetHighScore() + "");
        }
        if(Integer.parseInt(theModel.getScore()) > sessionHighScore) {
            sessionHighScore = Integer.parseInt(theModel.getScore());
            tvCurrentSessionHighScore.setText(sessionHighScore + "");
        }
    }

}
