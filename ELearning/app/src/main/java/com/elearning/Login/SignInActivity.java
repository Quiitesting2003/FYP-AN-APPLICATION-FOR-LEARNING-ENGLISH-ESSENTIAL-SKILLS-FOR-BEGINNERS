package com.elearning.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.elearning.Admin.AdminSignInActivity;
import com.elearning.Admin.DrawerActivity;
import com.elearning.HomePage.HomeActivity;
import com.elearning.Login.ForgotPasswordActivity;
import com.elearning.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private ImageView togglePasswordVisibilityImageView;
    private CheckBox rememberMeCheckBox;
    private ImageView signInButton;
    private ImageView googleImageView;
    private ImageView appleImageView;
    private ImageView notAccountImageView;
    private TextView forgotPassword;
    private TextView admin_signin;
    private ProgressBar progressBar;

    private SharedPreferences sharedPreferences;
    private boolean isPasswordVisible = false;

    // Firebase Auth instance
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        emailEditText = findViewById(R.id.et_email);
        passwordEditText = findViewById(R.id.et_password);
        togglePasswordVisibilityImageView = findViewById(R.id.iv_toggle_password);
        rememberMeCheckBox = findViewById(R.id.checkbox);
        signInButton = findViewById(R.id.sign_in);
        googleImageView = findViewById(R.id.gg);
        appleImageView = findViewById(R.id.apple);
        notAccountImageView = findViewById(R.id.not_account_sign_in);
        forgotPassword = findViewById(R.id.forgot_password);
        admin_signin = findViewById(R.id.admin_signin);
        progressBar = findViewById(R.id.progress_bar);
        ImageView emailTickImageView = findViewById(R.id.iv_email_ticks);

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


        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        admin_signin.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, AdminSignInActivity.class);
            startActivity(intent);
        });

        signInButton.setOnClickListener(v -> handleSignIn());

        forgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        togglePasswordVisibilityImageView.setOnClickListener(v -> {
            if (isPasswordVisible) {
                passwordEditText.setInputType(129);
                togglePasswordVisibilityImageView.setImageResource(R.drawable.visibilityoff);
            } else {
                passwordEditText.setInputType(1);
                togglePasswordVisibilityImageView.setImageResource(R.drawable.visibilityoff);
            }
            isPasswordVisible = !isPasswordVisible;
            passwordEditText.setSelection(passwordEditText.length());
        });

        notAccountImageView.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void handleSignIn() {
        progressBar.setVisibility(View.VISIBLE);
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(this, "Password must contain at least 6 characters, including 1 special character and 1 uppercase letter", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        // Firebase sign-in
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignInActivity.this, "Sign-in successful", Toast.LENGTH_SHORT).show();

                            // Save the user data in SharedPreferences if "Remember Me" is checked
                            if (rememberMeCheckBox.isChecked()) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("loggedInEmail", email);
                                editor.putBoolean("isLoggedIn", true);
                                editor.apply();
                            }

                            // Check if the user has completed their profile
                            checkUserProfile(user);
                        } else {
                            Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                            Log.e("SignIn", "Error: " + task.getException().getMessage());
                        }
                    }
                });
    }

    private void checkUserProfile(FirebaseUser user) {
        String userId = user.getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User profile exists, navigate to HomeActivity
                    Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    // No profile information, navigate to ProfileActivity for profile setup
                    Intent intent = new Intent(SignInActivity.this, ProfileActivity.class);
                    intent.putExtra("email", user.getEmail()); // Pass the email to ProfileActivity
                    startActivity(intent);
                }
                finish();  // Finish the SignInActivity to prevent going back
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SignInActivity.this, "Error retrieving user data", Toast.LENGTH_SHORT).show();
                Log.e("SignIn", "DatabaseError: " + databaseError.getMessage());
            }
        });
    }




    private boolean isFirstSignIn() {
        // Check SharedPreferences or Firebase to determine if this is the user's first time logging in
        return !sharedPreferences.contains("isLoggedIn") || !sharedPreferences.getBoolean("isLoggedIn", false);
    }


    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+=|<>?{}\\[\\]~-]).{8,}$";
        return password.matches(passwordPattern);
    }
}