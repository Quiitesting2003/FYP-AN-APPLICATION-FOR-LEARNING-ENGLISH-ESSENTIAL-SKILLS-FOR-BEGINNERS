package com.elearning.HomePage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;

import com.elearning.Login.SignInActivity;
import com.elearning.R;

import org.w3c.dom.Text;

public class AboutActivity extends AppCompatActivity {

    private Button backButton;
    private ImageView homeButton;
    private ImageView favButton;
    private ImageView dashboardButton;
    private ImageView aboutButton;
    private TextView aboutInfo;
    private TextView aboutSet;
    private TextView aboutPrivacy;
    private TextView aboutContact;
    private TextView aboutlogOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);

        homeButton = findViewById(R.id.about_home);

        dashboardButton = findViewById(R.id.about_dashboard);

        favButton = findViewById(R.id.about_fav);

        aboutButton = findViewById(R.id.about_about);

        aboutInfo = findViewById(R.id.about_info);
        aboutSet = findViewById(R.id.about_setting);
        aboutPrivacy = findViewById(R.id.about_privacy);
        aboutContact = findViewById(R.id.about_contact);
        aboutlogOut = findViewById(R.id.about_logOut);


        aboutContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, AboutContactActivity.class);
                startActivity(intent);
                finish();
            }
        });


        aboutPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, AboutPrivacyActivity.class);
                startActivity(intent);
                finish();
            }
        });


        aboutSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, AboutSettingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        aboutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, AboutInformationActivity.class);
                startActivity(intent);
                finish();
            }
        });


        aboutlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AboutActivity.this)
                        .setTitle("Log Out")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(AboutActivity.this, SignInActivity.class);
                                startActivity(intent);
                                finish(); // Close the current activity after logging out
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });


        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(aboutButton);

                Intent intent4 = new Intent(AboutActivity.this, AboutActivity.class);
                startActivity(intent4);
                finish();
            }
        });


        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(favButton);

                Intent intent4 = new Intent(AboutActivity.this, FavouriteActivity.class);
                startActivity(intent4);
                finish();
            }
        });

        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(dashboardButton);

                Intent intent3 = new Intent(AboutActivity.this, DashBoardActivity.class);
                startActivity(intent3);
                finish();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(homeButton);

                Intent intent1 = new Intent(AboutActivity.this, HomeActivity.class);
                startActivity(intent1);
                finish();
            }
        });

    }

    private void changeImageViewColor(ImageView imageView) {
        // Apply the blue tint color when clicked
        imageView.setColorFilter(getResources().getColor(R.color.blue), android.graphics.PorterDuff.Mode.SRC_IN);
    }
}