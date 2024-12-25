package com.elearning.Viet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.elearning.Viet.Intro3VnActivity;
import com.elearning.R;

public class Intro3VnActivity extends AppCompatActivity {

    private Button skipButton;
    private ImageView nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro3_vn);

        skipButton = findViewById(R.id.intro3_vn_skip);
        nextButton = findViewById(R.id.intro3_vn_next);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intro3VnActivity.this, GettingStartVnActivity.class);
                startActivity(intent);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intro3VnActivity.this, GettingStartVnActivity.class);
                startActivity(intent);
            }
        });
    }
}
