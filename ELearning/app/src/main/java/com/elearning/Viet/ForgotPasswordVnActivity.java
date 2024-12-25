package com.elearning.Viet;

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

public class ForgotPasswordVnActivity extends AppCompatActivity {

    private EditText emailEdittext;
    private ImageView emailVerificationImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password_vn);

        Button backButton = findViewById(R.id.backButton_forgotpassword_vn);
        emailEdittext = findViewById(R.id.et_via_email_vn);
        emailVerificationImage = findViewById(R.id.email_ticks); // Link the image view
        ImageView continueButton = findViewById(R.id.forgot_password_continue_button_vn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.elearning.Viet.ForgotPasswordVnActivity.this, SignInVnActivity.class);
                startActivity(intent);
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdittext.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(com.elearning.Viet.ForgotPasswordVnActivity.this, "Bạn phải nhập email của bạn để khôi phục mật khẩu", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(com.elearning.Viet.ForgotPasswordVnActivity.this, "Vui lòng nhập địa chỉ email hợp lệ", Toast.LENGTH_SHORT).show();
                } else {
                    // Show the verification image when email is valid
                    emailVerificationImage.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(com.elearning.Viet.ForgotPasswordVnActivity.this, CreateNewPasswordVnActivity.class);
                    intent.putExtra("user_email", email);
                    startActivity(intent);
                }
            }
        });
    }
}
