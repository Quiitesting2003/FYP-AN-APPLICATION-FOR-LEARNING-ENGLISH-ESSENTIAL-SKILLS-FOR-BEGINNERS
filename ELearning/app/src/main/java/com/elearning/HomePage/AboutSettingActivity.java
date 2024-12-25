package com.elearning.HomePage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import com.elearning.R;
import com.elearning.Viet.SignInVnActivity;
import com.elearning.VietHomePage.AboutSettingVnActivity;
import com.elearning.VietHomePage.AboutVnActivity;

public class AboutSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_setting);

        // Find the switch button and terms button in the layout
        Switch languageSwitch = findViewById(R.id.language_switch);
        TextView termBtn = findViewById(R.id.setting_term);

        Button backBtn = findViewById(R.id.setting_back);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutSettingActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        // Set listener for switch button changes
        languageSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Show confirmation dialog for language change
            new AlertDialog.Builder(AboutSettingActivity.this)
                    .setTitle("Language Change")
                    .setMessage("Do you want to change to Vietnamese?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Show second dialog for sign-in requirement
                        new AlertDialog.Builder(AboutSettingActivity.this)
                                .setTitle("Sign In Required")
                                .setMessage("Please sign in again to change the language.")
                                .setPositiveButton("OK", (dialog1, which1) -> {
                                    // Navigate to SignInVnActivity after confirmation
                                    Intent intent = new Intent(AboutSettingActivity.this, SignInVnActivity.class);
                                    startActivity(intent);
                                    finish(); // Optionally finish the current activity
                                })
                                .setCancelable(false)
                                .show();
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        // User canceled the action, reset the switch if needed
                        languageSwitch.setChecked(false);
                    })
                    .setCancelable(true)
                    .show();
        });

        // Set up the terms button to show the terms of use
        termBtn.setOnClickListener(v -> showTermsDialog());
    }

    // Method to show the terms of use dialog
    private void showTermsDialog() {
        String terms = "Terms of Use:\n\n" +
                "1. User Agreement: By using this app, you agree to these terms.\n\n" +
                "2. Eligibility: This app is intended for users aged 13 or older. Users under 13 may only use the app " +
                "with parental consent and supervision. We recommend that parents review the terms of use with their children.\n\n" +
                "3. Account Registration: You must create an account to access certain features. You are responsible for " +
                "the security of your login information. Any unauthorized use of your account should be reported immediately.\n\n" +
                "4. Content Usage: The content (lessons, quizzes, audio, etc.) is for personal, educational use only. You may not " +
                "copy, share, or modify any app content without written permission from us.\n\n" +
                "5. Learning Progress: Your progress is tracked to help improve your learning experience. However, this data is for personal use only " +
                "and does not constitute any official certification or educational record.\n\n" +
                "6. Subscription and Payments: Premium features require a subscription. A 7-day free trial is available for new users. " +
                "Your account will be charged after the trial period unless the subscription is canceled. You can cancel your subscription at any time.\n\n" +
                "7. Refunds: Subscription payments are non-refundable once the trial period has ended. If you encounter issues or require assistance, " +
                "please contact our support team.\n\n" +
                "8. Termination of Use: We reserve the right to terminate or suspend your account if you violate these terms or engage in unlawful activities. " +
                "Upon termination, you may lose access to your data and content.\n\n" +
                "9. User Conduct: You agree not to engage in any activity that disrupts the app’s services or harms other users. " +
                "This includes posting inappropriate content, impersonating others, or engaging in harassment.\n\n" +
                "10. Privacy and Data Collection: We collect certain personal data to improve the app experience, including name, email, and learning progress. " +
                "Please refer to our Privacy Policy for detailed information about how we collect, use, and protect your data.\n\n" +
                "11. Age Restrictions: Users under 13 may only use the app with parental guidance. We encourage parents to monitor their children’s activities within the app.\n\n" +
                "12. Third-Party Services: We may integrate services from third parties (e.g., analytics or ads). These services have their own terms, and " +
                "we encourage users to review their policies. We are not responsible for the actions of third-party services.\n\n" +
                "13. Modifications to Terms: We reserve the right to modify these terms of use at any time. You will be notified of significant changes via " +
                "email or app notifications. Continued use of the app after changes implies your acceptance of the new terms.\n\n" +
                "14. Intellectual Property: All app content, logos, and designs are the intellectual property of [App Name]. You may not use any materials " +
                "from the app for commercial purposes without prior approval.\n\n" +
                "15. Limitation of Liability: We provide the app ‘as is’ without any warranties. We are not responsible for any damages that result from the " +
                "use or inability to use the app, including data loss or service interruptions.\n\n" +
                "16. Contact Information: For any questions or concerns regarding these terms, please contact us at support@yourapp.com or visit the Help section within the app.\n";

        new AlertDialog.Builder(AboutSettingActivity.this)
                .setTitle("Terms of Use")
                .setMessage(terms)
                .setPositiveButton("OK", null)
                .setCancelable(true)
                .show();
    }

    // Move openIntroPage() method outside onCreate()
    private void openIntroPage() {
        Intent intent = new Intent(AboutSettingActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
