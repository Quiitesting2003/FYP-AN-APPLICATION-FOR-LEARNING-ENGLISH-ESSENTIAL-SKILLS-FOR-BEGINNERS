package com.elearning.Present;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.elearning.Login.SignUpActivity;
import com.elearning.R;

public class PresentSignActivity extends AppCompatActivity {
    private Button nextBtn, backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_present_sign);

        nextBtn = findViewById(R.id.nextPresent);
        backBtn = findViewById(R.id.backPresent);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PresentSignActivity.this, IntroPresentActivity.class);
                startActivity(intent);
                finish();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PresentSignActivity.this, IntroPresentActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}