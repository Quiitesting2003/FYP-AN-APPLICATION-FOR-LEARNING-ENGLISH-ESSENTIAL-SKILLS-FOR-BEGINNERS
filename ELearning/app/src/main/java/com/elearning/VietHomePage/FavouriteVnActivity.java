package com.elearning.VietHomePage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.elearning.R;

public class FavouriteVnActivity extends AppCompatActivity {

    private ImageView homeButton;
    private ImageView favButton;
    private ImageView dashboardButton;
    private ImageView aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favourite_vn);

        homeButton = findViewById(R.id.about_home);

        dashboardButton = findViewById(R.id.about_dashboard);

        favButton = findViewById(R.id.about_fav);

        aboutButton = findViewById(R.id.about_about);


        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(aboutButton);

                Intent intent = new Intent(FavouriteVnActivity.this, AboutVnActivity.class);
                startActivity(intent);
                finish();
            }
        });


        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(favButton);

                Intent intent = new Intent(FavouriteVnActivity.this, FavouriteVnActivity.class);
                startActivity(intent);
                finish();
            }
        });

        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(dashboardButton);

                Intent intent = new Intent(FavouriteVnActivity.this, DashBoardVnActivity.class);
                startActivity(intent);
                finish();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(homeButton);

                Intent intent = new Intent(FavouriteVnActivity.this, HomeVnActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void changeImageViewColor(ImageView imageView) {
        // Apply the blue tint color when clicked
        imageView.setColorFilter(getResources().getColor(R.color.blue), android.graphics.PorterDuff.Mode.SRC_IN);
    }
}