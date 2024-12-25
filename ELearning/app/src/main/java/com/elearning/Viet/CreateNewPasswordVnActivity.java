package com.elearning.Viet;

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

public class CreateNewPasswordVnActivity extends AppCompatActivity {

    private EditText newPasswordEditText, confirmPasswordEditText, currentPasswordEditText;
    private ImageView togglePassword1, togglePassword2, togglePassword0;
    private boolean isPasswordVisible1 = false;
    private boolean isPasswordVisible2 = false;
    private boolean isPasswordVisible0 = false;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_password_vn);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        Button backButton = findViewById(R.id.trolai_password_vn);
        newPasswordEditText = findViewById(R.id.newPassword_vn);
        confirmPasswordEditText = findViewById(R.id.confirmPassword_vn);
        currentPasswordEditText = findViewById(R.id.et_current_password);  // New EditText for current password
        togglePassword1 = findViewById(R.id.togglePassword1_vn);
        togglePassword2 = findViewById(R.id.togglePassword2_vn);
        togglePassword0 = findViewById(R.id.togglePassword0);  // Toggle for the current password visibility
        ImageView continueButton = findViewById(R.id.tieptuc_matkhau_vn);

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
                showToast("Vui lòng nhập mật khẩu mới của bạn");
            } else if (confirmPassword.isEmpty()) {
                showToast("Vui lòng xác nhận mật khẩu mới của bạn");
            } else if (!newPassword.equals(confirmPassword)) {
                showToast("Mật khẩu không khớp");
            } else if (!isValidPassword(newPassword)) {
                showToast("Mật khẩu phải dài ít nhất 8 ký tự và chứa ít nhất một ký tự đặc biệt và một chữ cái");
            } else if (currentPassword.isEmpty()) {
                showToast("Vui lòng nhập mật khẩu hiện tại của bạn");
            } else {
                FirebaseUser user = mAuth.getCurrentUser();

                // If the user is not logged in, initiate password reset
                if (user == null) {
                    showToast("Người dùng chưa đăng nhập. Đang khởi tạo lại mật khẩu...");
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
        Toast.makeText(CreateNewPasswordVnActivity.this, message, Toast.LENGTH_SHORT).show();
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
                        showToast("Đã gửi email đặt lại mật khẩu. Vui lòng kiểm tra hộp thư đến của bạn.");
                    } else {
                        Log.e("Password Reset", "Error sending reset email", task.getException());
                        showToast("Không gửi được email đặt lại mật khẩu.");
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
                    showToast("Xác thực lại không thành công. Vui lòng kiểm tra mật khẩu hiện tại của bạn.");
                }
            });
        }
    }

    // Method to update the password in Firebase
    private void updatePassword(FirebaseUser user, String newPassword) {
        user.updatePassword(newPassword)
                .addOnCompleteListener(updateTask -> {
                    if (updateTask.isSuccessful()) {
                        showToast("Mật khẩu đã được cập nhật thành công");

                        // Optionally navigate to SignInActivity or another screen
                        Intent intent = new Intent(CreateNewPasswordVnActivity.this, SignInVnActivity.class);
                        startActivity(intent);
                        finish(); // Close the current activity
                    } else {
                        showToast("Không cập nhật được mật khẩu");
                    }
                });
    }
}
