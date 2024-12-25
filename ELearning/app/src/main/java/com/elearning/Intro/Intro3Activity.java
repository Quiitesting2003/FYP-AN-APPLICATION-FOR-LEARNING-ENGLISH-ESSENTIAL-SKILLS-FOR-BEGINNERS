package com.elearning.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.elearning.Login.GettingStartedActivity;
import com.elearning.R;

public class Intro3Activity extends AppCompatActivity {

    private Button skipButton;
    private ImageView nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro3);

        skipButton = findViewById(R.id.intro3_skip);
        nextButton = findViewById(R.id.intro3_next);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to go to Intro2VnActivity
                Intent intent = new Intent(Intro3Activity.this, GettingStartedActivity.class);
                startActivity(intent);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to go to Intro2VnActivity
                Intent intent = new Intent(Intro3Activity.this, GettingStartedActivity.class);
                startActivity(intent);
            }
        });
    }
}
