package com.elearning.Admin;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.elearning.Admin.AdminSignInActivity;
import com.elearning.Admin.DrawerActivity;
import com.elearning.Login.ForgotPasswordActivity;
import com.elearning.Login.SignInActivity;
import com.elearning.R;

public class AdminSignInActivity extends AppCompatActivity {

    private EditText adminEmail, adminPassword;

    private ImageView signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.elearning.R.layout.activity_admin_sign_in);

        // Initialize views
        adminEmail = findViewById(R.id.admin_email);
        adminPassword = findViewById(R.id.admin_password);
        signInButton = findViewById(R.id.admin_signin);

        // Sign in button click listener
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = adminEmail.getText().toString().trim();
                String password = adminPassword.getText().toString().trim();

                if (validateInput(email, password)) {
                    // Proceed with admin login logic (e.g., authentication)
                    signInAdmin(email, password);
                }
            }
        });

        // Email input text change listener
        adminEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Optionally handle input changes
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Optionally handle input changes
            }
        });
    }

    // Validate email and password input
    private boolean validateInput(String email, String password) {
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Sign in admin (authentication logic goes here)
    private void signInAdmin(String email, String password) {
        // Implement your authentication logic here (e.g., Firebase Auth or your server authentication)
        // For demonstration, showing a success message
        Toast.makeText(this, "Admin signed in successfully!", Toast.LENGTH_SHORT).show();

        // Navigate to Admin Dashboard or Main Activity after successful sign in
        Intent intent = new Intent(AdminSignInActivity.this, DrawerActivity.class);
        startActivity(intent);
        finish(); // Finish the sign-in activity so the user can't go back to it
    }
}
