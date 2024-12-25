package com.elearning.VietHomePage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.elearning.R;
import android.util.Patterns;

public class ChangePasswordVnActivity extends AppCompatActivity {

    private EditText emailEdittext;
    private ImageView emailVerificationImage; // Add the ImageView for email verification

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password_vn);

        Button backButton = findViewById(R.id.changePass_vn_back);
        emailEdittext = findViewById(R.id.changePass_vn);
        emailVerificationImage = findViewById(R.id.email_ticks);
        ImageView continueButton = findViewById(R.id.changePass_vn_continue);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePasswordVnActivity.this, AboutVnActivity.class);
                startActivity(intent);
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdittext.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(ChangePasswordVnActivity.this, "You have to enter your email to change password", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(ChangePasswordVnActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                } else {
                    // Show the verification image when email is valid
                    emailVerificationImage.setVisibility(View.VISIBLE);

                    // Proceed to the next activity
                    Intent intent = new Intent(ChangePasswordVnActivity.this, ConfirmChangePasswordVnActivity.class);
                    intent.putExtra("user_email", email);
                    startActivity(intent);
                }
            }
        });
    }
}
