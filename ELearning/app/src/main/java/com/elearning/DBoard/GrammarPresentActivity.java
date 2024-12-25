package com.elearning.DBoard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.elearning.HomePage.DashBoardActivity;
import com.elearning.Present.InsidePresentActivity;
import com.elearning.Present.IntroPresentActivity;
import com.elearning.R;

public class GrammarPresentActivity extends AppCompatActivity {

    private Button simplePresent, simpleProg, simplePer, simplePerProg, backBtn;
    private ImageView vocHeartOne, vocHeartTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grammar_present);

        simplePresent = findViewById(R.id.gm_presentSimple);
        simpleProg = findViewById(R.id.gm_presentProg);
        simplePer = findViewById(R.id.gm_presentPer);
        simplePerProg = findViewById(R.id.gm_presentPerProg);
        vocHeartOne = findViewById(R.id.voc_heart_one);
        vocHeartTwo = findViewById(R.id.voc_heart_two);
        backBtn = findViewById(R.id.grpr_back);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GrammarPresentActivity.this, GrammarActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set OnClickListener for the "Lesson 1" button
        simplePresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to open InsideVocabularyActivity
                Intent intent = new Intent(GrammarPresentActivity.this, IntroPresentActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Optional: Set up the heart click listener (to toggle favorite status)
        vocHeartOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the heart image between black and red
                if (vocHeartOne.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.black_heart).getConstantState())) {
                    vocHeartOne.setImageResource(R.drawable.red_heart);  // Change to red heart when clicked
                } else {
                    vocHeartOne.setImageResource(R.drawable.black_heart);  // Change back to black heart when clicked
                }
                Toast.makeText(GrammarPresentActivity.this, "Add to Favourite", Toast.LENGTH_SHORT).show();
            }
        });

        // Set OnClickListener for the "Lesson 1" button
        simpleProg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to open InsideVocabularyActivity

            }
        });

        // Optional: Set up the heart click listener (to toggle favorite status)
        vocHeartTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the heart image between black and red
                if (vocHeartOne.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.black_heart).getConstantState())) {
                    vocHeartOne.setImageResource(R.drawable.red_heart);  // Change to red heart when clicked
                } else {
                    vocHeartOne.setImageResource(R.drawable.black_heart);  // Change back to black heart when clicked
                }
                Toast.makeText(GrammarPresentActivity.this, "Add to Favourite", Toast.LENGTH_SHORT).show();
            }
        });



    }
}