package com.elearning.VietHomePage;

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

public class AboutVnActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_about_vn);

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
                Intent intent = new Intent(AboutVnActivity.this, AboutContactVnActivity.class);
                startActivity(intent);
                finish();
            }
        });


        aboutPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutVnActivity.this, AboutPrivacyVnActivity.class);
                startActivity(intent);
                finish();
            }
        });


        aboutSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutVnActivity.this, AboutSettingVnActivity.class);
                startActivity(intent);
                finish();
            }
        });

        aboutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutVnActivity.this, AboutInformationVnActivity.class);
                startActivity(intent);
                finish();
            }
        });


        aboutlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AboutVnActivity.this)
                        .setTitle("Đăng xuất")
                        .setMessage("Bạn có chắc chắn muốn đăng xuất không?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(AboutVnActivity.this, SignInActivity.class);
                                startActivity(intent);
                                finish(); // Close the current activity after logging out
                            }
                        })
                        .setNegativeButton("Không", null)
                        .show();
            }
        });


        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(aboutButton);

                Intent intent4 = new Intent(AboutVnActivity.this, AboutVnActivity.class);
                startActivity(intent4);
                finish();
            }
        });


        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(favButton);

                Intent intent4 = new Intent(AboutVnActivity.this, FavouriteVnActivity.class);
                startActivity(intent4);
                finish();
            }
        });

        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(dashboardButton);

                Intent intent3 = new Intent(AboutVnActivity.this, DashBoardVnActivity.class);
                startActivity(intent3);
                finish();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(homeButton);

                Intent intent1 = new Intent(AboutVnActivity.this, HomeVnActivity.class);
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