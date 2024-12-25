package com.elearning.Viet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.elearning.Viet.SignInVnActivity;
import com.elearning.Viet.SignUpVnActivity;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.elearning.Viet.GettingStartVnActivity;
import com.elearning.R;

public class GettingStartVnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_getting_start_vn);

        ImageView signInVn = findViewById(R.id.sign_in_account_Vn);
        ImageView signUpVn = findViewById(R.id.not_account_sign_in_Vn);


        signInVn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GettingStartVnActivity.this, SignInVnActivity.class);
                startActivity(intent);
            }
        });

        signUpVn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GettingStartVnActivity.this, SignUpVnActivity.class);
                startActivity(intent);
            }
        });
    }
}