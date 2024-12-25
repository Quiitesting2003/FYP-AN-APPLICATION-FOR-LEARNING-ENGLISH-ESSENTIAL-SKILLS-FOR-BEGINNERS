package com.elearning.VietHomePage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.elearning.R;

public class HomeVnActivity extends AppCompatActivity {

    private ImageView homeAbout;
    private ImageView dashBoard;
    private ImageView favourite;
    private ImageView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_vn);

        homeAbout = findViewById(R.id.homevn_about);
        dashBoard = findViewById(R.id.homevn_dashboard);
        favourite = findViewById(R.id.homevn_fav);
        home = findViewById(R.id.homevn_home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(home);

                Intent intent1 = new Intent(HomeVnActivity.this, HomeVnActivity.class);
                startActivity(intent1);

            }
        });

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(favourite);

                Intent intent2 = new Intent(HomeVnActivity.this, DashBoardVnActivity.class);
                startActivity(intent2);

            }
        });

        dashBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(dashBoard);

                Intent intent3 = new Intent(HomeVnActivity.this, DashBoardVnActivity.class);
                startActivity(intent3);

            }
        });

        homeAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change color of home_about ImageView
                changeImageViewColor(homeAbout);

                // Start AboutActivity
                Intent intent4 = new Intent(HomeVnActivity.this, AboutVnActivity.class);
                startActivity(intent4);
            }
        });
    }

    private void changeImageViewColor(ImageView imageView) {
        // Apply the blue tint color when clicked
        imageView.setColorFilter(getResources().getColor(R.color.blue), android.graphics.PorterDuff.Mode.SRC_IN);
    }
}
