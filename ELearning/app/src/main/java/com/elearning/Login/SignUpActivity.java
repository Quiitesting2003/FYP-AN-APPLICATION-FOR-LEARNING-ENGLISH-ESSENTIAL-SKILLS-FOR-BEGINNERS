package com.elearning.Login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;

import com.elearning.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private ImageView togglePasswordVisibilityImageView;
    private CheckBox termsAndConditionsCheckBox;
    private ImageView signUpButton;
    private ImageView googleImageView;
    private ImageView appleImageView;
    private ImageView haveAccountImageView;
    private TextView tvAgree;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize views
        emailEditText = findViewById(R.id.et_email);
        passwordEditText = findViewById(R.id.et_password);
        togglePasswordVisibilityImageView = findViewById(R.id.iv_toggle_password);
        termsAndConditionsCheckBox = findViewById(R.id.checkbox);
        signUpButton = findViewById(R.id.sign_up);
        googleImageView = findViewById(R.id.gg);
        appleImageView = findViewById(R.id.apple);
        haveAccountImageView = findViewById(R.id.not_account_signin);
        tvAgree = findViewById(R.id.tv_agree);
        progressBar = findViewById(R.id.progress_bar);
        ImageView emailTickImageView = findViewById(R.id.iv_email_tick);

        // Add a TextWatcher to validate email
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String email = s.toString().trim();
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailTickImageView.setVisibility(View.VISIBLE);
                } else {
                    emailTickImageView.setVisibility(View.GONE);
                }
            }
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTermsAndConditionsDialog();
            }
        });

        // Set up listeners
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignUp();
            }
        });

        togglePasswordVisibilityImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordEditText.getTransformationMethod() instanceof android.text.method.PasswordTransformationMethod) {
                    passwordEditText.setTransformationMethod(null); // Show password
                    togglePasswordVisibilityImageView.setImageResource(R.drawable.visibilityoff);
                } else {
                    passwordEditText.setTransformationMethod(new android.text.method.PasswordTransformationMethod()); // Hide password
                    togglePasswordVisibilityImageView.setImageResource(R.drawable.visibilityoff);
                }
            }
        });

        haveAccountImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showTermsAndConditionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Terms and Conditions");
        String termsConditions = "Acceptance of Terms\n" +
                "By downloading and using [App Name] (\"App\"), you agree to these Terms and Conditions. If you do not agree, do not use the App.\n" +
                "\n" +
                "License\n" +
                "[App Name] grants you a limited, non-exclusive, non-transferable license to use the App for personal, non-commercial purposes. You may not modify, distribute, or create derivative works based on the App without permission.\n" +
                "\n" +
                "Account Security\n" +
                "You are responsible for maintaining the confidentiality of your account details, including username and password. You are also responsible for all activities under your account. Notify us immediately if you suspect any unauthorized use of your account.\n" +
                "\n" +
                "Content\n" +
                "All content within the App, including text, images, and videos, is protected by copyright and intellectual property laws. You may not reproduce, distribute, or exploit any part of the App’s content without prior written consent.\n" +
                "\n" +
                "Modifications and Updates\n" +
                "We may update, modify, or discontinue the App at any time without notice. We are not liable for any changes, interruptions, or errors in the App’s operation.\n" +
                "\n" +
                "Limitation of Liability\n" +
                "The App is provided \"as is\" and we are not responsible for any damages or losses arising from its use. For more information, please refer to our [Privacy Policy]. By using the App, you agree to these terms.";
        builder.setMessage(termsConditions);

        builder.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SignUpActivity.this, "You have agreed to the Terms and Conditions.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void handleSignUp() {
        progressBar.setVisibility(View.VISIBLE);
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(this, "Password must have 6 words and contain at least 1 special character and 1 alphabet letter", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        // Use Firebase to create an account
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign-up successful, redirect to SignInActivity
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignUpActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign-up fails, display a message to the user
                            Toast.makeText(SignUpActivity.this, "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+=|<>?{}\\[\\]~-]).{8,}$";
        return password.matches(passwordPattern);
    }
}
