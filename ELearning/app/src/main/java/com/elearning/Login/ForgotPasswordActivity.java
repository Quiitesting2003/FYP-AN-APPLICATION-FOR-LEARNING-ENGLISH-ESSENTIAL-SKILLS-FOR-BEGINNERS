package com.elearning.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.util.Patterns;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.elearning.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEdittext;
    private ImageView emailVerificationImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        Button backButton = findViewById(R.id.backButton_forgotpassword);
        emailEdittext = findViewById(R.id.et_via_email);
        emailVerificationImage = findViewById(R.id.email_ticks); // Link the image view
        ImageView continueButton = findViewById(R.id.forgot_password_continue_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.elearning.Login.ForgotPasswordActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdittext.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(com.elearning.Login.ForgotPasswordActivity.this, "You have to enter your email to recover password", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(com.elearning.Login.ForgotPasswordActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                } else {
                    // Show the verification image when email is valid
                    emailVerificationImage.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(com.elearning.Login.ForgotPasswordActivity.this, CreateNewPasswordActivity.class);
                    intent.putExtra("user_email", email);
                    startActivity(intent);
                }
            }
        });
    }
}
