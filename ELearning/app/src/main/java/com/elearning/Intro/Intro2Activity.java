package com.elearning.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.elearning.R;

public class Intro2Activity extends AppCompatActivity {

    private Button skipButton;
    private ImageView nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro2);

        skipButton = findViewById(R.id.intro2_skip);
        nextButton = findViewById(R.id.intro2_next);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to go to Intro2VnActivity
                Intent intent = new Intent(Intro2Activity.this, Intro3Activity.class);
                startActivity(intent);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to go to Intro2VnActivity
                Intent intent = new Intent(Intro2Activity.this, Intro3Activity.class);
                startActivity(intent);
            }
        });
    }
}
