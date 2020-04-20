package com.shaheen.higherlower;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * @author Derek Shaheen
 * @date: Apr 17, 2020
 * @version: 1.0
 */

public class MainActivity extends AppCompatActivity {

    ImageButton buttonHigher;
    ImageButton buttonLower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupCallButtonClickEvents();
    }

    private void setupCallButtonClickEvents() {
        buttonHigher = (ImageButton) findViewById(R.id.btnHigher);
        buttonHigher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //
                Log.d("out", "Working great!");
            }
        });

        buttonLower = (ImageButton) findViewById(R.id.btnLower);
        buttonLower.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //
                Log.d("out", "Looking good, yo!");
            }
        });
    }


}
