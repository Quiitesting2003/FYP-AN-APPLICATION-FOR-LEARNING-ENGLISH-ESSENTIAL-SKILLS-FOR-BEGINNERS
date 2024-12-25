package com.elearning.Login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.elearning.Login.GettingStartedActivity;
import com.elearning.R;
import com.elearning.Viet.SignInVnActivity;
import com.elearning.Viet.SignUpVnActivity;

public class GettingStartedActivity extends AppCompatActivity {

    private ImageView signIn;
    private ImageView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);

        signIn = findViewById(R.id.sign_in_account);
        signUp = findViewById(R.id.not_account_sign_in);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GettingStartedActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GettingStartedActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
