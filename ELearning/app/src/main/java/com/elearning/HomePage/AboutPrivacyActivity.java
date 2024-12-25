package com.elearning.HomePage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.elearning.R;
import androidx.activity.EdgeToEdge;

public class AboutPrivacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_privacy);

        // Find the TextView by its ID
        TextView btnPolicy = findViewById(R.id.privacy_policy);
        Button backBtn = findViewById(R.id.privacy_back);
        TextView changePass = findViewById(R.id.privacy_changePass);

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an AlertDialog to confirm the password change
                new AlertDialog.Builder(AboutPrivacyActivity.this)
                        .setTitle("Change Password")
                        .setMessage("Are you sure you want to change your password?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // If the user confirms, start the ChangePasswordActivity
                                Intent intent = new Intent(AboutPrivacyActivity.this, ChangePasswordActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null) // Dismiss if "No" is clicked
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AboutPrivacyActivity.this, AboutActivity.class);
                startActivity(intent1);
            }
        });

        // Set an OnClickListener on the TextView (used as a button here)
        btnPolicy.setOnClickListener(view -> showPolicyDialog());
    }

    // Method to display the policy in an AlertDialog
    private void showPolicyDialog() {
        // Define the policy text
        String policyText = "Privacy Policy:\n"
                + "1. Data Collection: We collect user data to improve app functionality. "
                + "This data is securely stored and never shared with third parties.\n"
                + "2. Permissions: We request access to the microphone for voice exercises.\n"
                + "3. Subscription: Payment is required for premium features, with an unlimited trial usage.\n"
                + "4. Content Accuracy: All learning materials are regularly updated for accuracy.\n"
                + "5. Feedback: You can report bugs or suggest features through the Help section.\n";

        // Create and show the AlertDialog
        new AlertDialog.Builder(this)
                .setTitle("App Policy")
                .setMessage(policyText)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }
}
