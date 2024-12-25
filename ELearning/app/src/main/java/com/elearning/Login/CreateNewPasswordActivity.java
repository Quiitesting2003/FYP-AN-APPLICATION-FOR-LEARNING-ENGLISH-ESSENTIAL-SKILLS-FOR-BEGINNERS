package com.elearning.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elearning.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;

public class CreateNewPasswordActivity extends AppCompatActivity {

    private EditText newPasswordEditText, confirmPasswordEditText, currentPasswordEditText;
    private ImageView togglePassword1, togglePassword2, togglePassword0;
    private boolean isPasswordVisible1 = false;
    private boolean isPasswordVisible2 = false;
    private boolean isPasswordVisible0 = false;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_password);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        Button backButton = findViewById(R.id.backbutton_newpassword);
        newPasswordEditText = findViewById(R.id.et_password_new);
        confirmPasswordEditText = findViewById(R.id.et_password_confirm);
        currentPasswordEditText = findViewById(R.id.et_current_password);  // New EditText for current password
        togglePassword1 = findViewById(R.id.togglePassword1);
        togglePassword2 = findViewById(R.id.togglePassword2);
        togglePassword0 = findViewById(R.id.togglePassword0);  // Toggle for the current password visibility
        ImageView continueButton = findViewById(R.id.new_account_continue_button);

        // Back button listener
        backButton.setOnClickListener(v -> finish()); // Go back to the previous screen

        // Toggle password visibility for current password field
        togglePassword0.setOnClickListener(v -> togglePasswordVisibility(currentPasswordEditText, togglePassword0));

        // Toggle password visibility for new password field
        togglePassword1.setOnClickListener(v -> togglePasswordVisibility(newPasswordEditText, togglePassword1));

        // Toggle password visibility for confirm password field
        togglePassword2.setOnClickListener(v -> togglePasswordVisibility(confirmPasswordEditText, togglePassword2));

        // Continue button listener with password validation
        continueButton.setOnClickListener(v -> {
            String newPassword = newPasswordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();
            String currentPassword = currentPasswordEditText.getText().toString().trim();  // Get current password

            // Password validation checks
            if (newPassword.isEmpty()) {
                showToast("Please enter your new password");
            } else if (confirmPassword.isEmpty()) {
                showToast("Please confirm your new password");
            } else if (!newPassword.equals(confirmPassword)) {
                showToast("Passwords do not match");
            } else if (!isValidPassword(newPassword)) {
                showToast("Password must be at least 8 characters long and contain at least one special character and one letter");
            } else if (currentPassword.isEmpty()) {
                showToast("Please enter your current password");
            } else {
                FirebaseUser user = mAuth.getCurrentUser();

                // If the user is not logged in, initiate password reset
                if (user == null) {
                    showToast("User not logged in. Initiating password reset...");
                    initiatePasswordReset();
                } else {
                    // If user is logged in, proceed with reauthentication
                    reauthenticateAndUpdatePassword(user, currentPassword, newPassword);
                }
            }
        });
    }

    // Helper method to show Toast messages
    private void showToast(String message) {
        Toast.makeText(CreateNewPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    // Helper method to toggle password visibility
    private void togglePasswordVisibility(EditText passwordEditText, ImageView toggleImageView) {
        if (isPasswordVisible1) {
            passwordEditText.setInputType(129); // textPassword type
            toggleImageView.setImageResource(R.drawable.visibilityoff); // Set icon to visibility off
        } else {
            passwordEditText.setInputType(1); // text type
            toggleImageView.setImageResource(R.drawable.visibilityoff); // Set icon to visibility on
        }
        isPasswordVisible1 = !isPasswordVisible1;
        passwordEditText.setSelection(passwordEditText.length()); // Move cursor to the end
    }

    // Helper method to validate password strength
    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters and contain one letter and one special character
        String passwordPattern = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+=|<>?{}\\[\\]~-]).{8,}$";
        return password.matches(passwordPattern);
    }

    // Method to initiate password reset flow
    private void initiatePasswordReset() {
        String email = "user@example.com"; // Get user's email address, which you would normally get from the UI or user input

        // Send password reset email
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        showToast("Password reset email sent. Please check your inbox.");
                    } else {
                        Log.e("Password Reset", "Error sending reset email", task.getException());
                        showToast("Failed to send password reset email.");
                    }
                });
    }

    // Method to reauthenticate the user before updating password
    private void reauthenticateAndUpdatePassword(FirebaseUser user, String currentPassword, String newPassword) {
        String email = user.getEmail();
        if (email != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(email, currentPassword);

            // Reauthenticate the user
            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Now that the user is reauthenticated, update the password
                    updatePassword(user, newPassword);
                } else {
                    Log.e("Reauthentication", "Reauthentication failed", task.getException());
                    showToast("Reauthentication failed. Please check your current password.");
                }
            });
        }
    }

    // Method to update the password in Firebase
    private void updatePassword(FirebaseUser user, String newPassword) {
        user.updatePassword(newPassword)
                .addOnCompleteListener(updateTask -> {
                    if (updateTask.isSuccessful()) {
                        showToast("Password updated successfully");

                        // Optionally navigate to SignInActivity or another screen
                        Intent intent = new Intent(CreateNewPasswordActivity.this, SignInActivity.class);
                        startActivity(intent);
                        finish(); // Close the current activity
                    } else {
                        showToast("Failed to update password");
                    }
                });
    }
}
