package com.elearning.DBoard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.elearning.HomePage.DashBoardActivity;
import com.elearning.R;

public class GrammarActivity extends AppCompatActivity {

    private Button simpleBtn, pastBtn, futureBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grammar);

        simpleBtn = findViewById(R.id.simple);
        pastBtn = findViewById(R.id.past);
        futureBtn = findViewById(R.id.future);
        backBtn = findViewById(R.id.gr_back);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GrammarActivity.this, DashBoardActivity.class);
                startActivity(intent);
                finish();            }
        });

        simpleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GrammarActivity.this, GrammarPresentActivity.class);
                startActivity(intent);
                finish();            }
        });

        pastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GrammarActivity.this, GrammarPastActivity.class);
                startActivity(intent);
                finish();            }
        });

        futureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GrammarActivity.this, GrammarFutureActivity.class);
                startActivity(intent);
                finish();            }
        });

    }
}