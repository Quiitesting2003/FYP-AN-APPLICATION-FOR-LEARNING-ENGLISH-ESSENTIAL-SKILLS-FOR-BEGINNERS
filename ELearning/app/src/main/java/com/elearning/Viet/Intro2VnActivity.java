package com.elearning.Viet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.elearning.R;

public class Intro2VnActivity extends AppCompatActivity {

    private Button skipButton;
    private ImageView nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro2_vn);

        skipButton = findViewById(R.id.intro2_vn_skip);
        nextButton = findViewById(R.id.intro2_vn_next);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intro2VnActivity.this, Intro3VnActivity.class);
                startActivity(intent);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intro2VnActivity.this, Intro3VnActivity.class);
                startActivity(intent);
            }
        });
    }
}
