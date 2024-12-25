package com.elearning.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.elearning.R;

public class Intro1Activity extends AppCompatActivity {

    private Button skipButton;
    private ImageView nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro1);

        skipButton = findViewById(R.id.intro1_skip);
        nextButton = findViewById(R.id.intro1_next);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to go to Intro2VnActivity
                Intent intent = new Intent(Intro1Activity.this, Intro2Activity.class);
                startActivity(intent);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to go to Intro2VnActivity
                Intent intent = new Intent(Intro1Activity.this, Intro2Activity.class);
                startActivity(intent);
            }
        });
    }
}
