package com.elearning.Present;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.elearning.DBoard.ScoreActivity;
import com.elearning.DBoard.VocabularyActivity;
import com.elearning.R;

public class IntroPresentActivity extends AppCompatActivity {
    private ImageView arrowOneIv, arrowTwoIv, arrowThreeIv;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_present);

        arrowOneIv = findViewById(R.id.arrowPresent_one);
        arrowTwoIv = findViewById(R.id.arrowPresent_two);
        arrowThreeIv = findViewById(R.id.arrowPresent_three);
        nextBtn = findViewById(R.id.introPresent_next);

        arrowOneIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroPresentActivity.this, PresentWhatActivity.class);
                startActivity(intent);
                finish();
            }
        });

        arrowTwoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroPresentActivity.this, PresentHowActivity.class);
                startActivity(intent);
                finish();
            }
        });

        arrowThreeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroPresentActivity.this, PresentSignActivity.class);
                startActivity(intent);
                finish();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroPresentActivity.this, InsidePresentActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
