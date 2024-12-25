package com.elearning.DBoard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.elearning.HomePage.DashBoardActivity;
import com.elearning.HomePage.FavouriteActivity;
import com.elearning.R;

import java.util.HashSet;
import java.util.Set;

public class PronunciationActivity extends AppCompatActivity {

    private Set<String> favoriteLessons = new HashSet<>();
    private Button backBtn, proOne, proTwo, proThree, proFour, proFive, proSix, proSeven, proEight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pronunciation);

        // Retrieve saved favorite lessons from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("FavoritesPrefs", Context.MODE_PRIVATE);
        favoriteLessons = sharedPreferences.getStringSet("favorites", new HashSet<>());

        backBtn = findViewById(R.id.pro_back);
        proOne = findViewById(R.id.pro_one);

        proOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PronunciationActivity.this, InsidePronunciationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PronunciationActivity.this, DashBoardActivity.class);
                startActivity(intent);
            }
        });

        // Find the heart ImageViews by their IDs
        ImageView heart1 = findViewById(R.id.pro_heart_one);
        ImageView heart2 = findViewById(R.id.pro_heart_two);
        ImageView heart3 = findViewById(R.id.pro_heart_three);
        ImageView heart4 = findViewById(R.id.pro_heart_four);
        ImageView heart5 = findViewById(R.id.pro_heart_five);
        ImageView heart6 = findViewById(R.id.pro_heart_six);
        ImageView heart7 = findViewById(R.id.pro_heart_seven);
        ImageView heart8 = findViewById(R.id.pro_heart_eight);

        // Set up heart click listeners
        setHeartClickListener(heart1, "Lesson 1");
        setHeartClickListener(heart2, "Lesson 2");
        setHeartClickListener(heart3, "Lesson 3");
        setHeartClickListener(heart4, "Lesson 4");
        setHeartClickListener(heart5, "Lesson 5");
        setHeartClickListener(heart6, "Lesson 6");
        setHeartClickListener(heart7, "Lesson 7");
        setHeartClickListener(heart8, "Lesson 8");

        // Initialize heart states based on favorites
        initializeHeartStates(heart1, "Lesson 1");
        initializeHeartStates(heart2, "Lesson 2");
        initializeHeartStates(heart3, "Lesson 3");
        initializeHeartStates(heart4, "Lesson 4");
        initializeHeartStates(heart5, "Lesson 5");
        initializeHeartStates(heart6, "Lesson 6");
        initializeHeartStates(heart7, "Lesson 7");
        initializeHeartStates(heart8, "Lesson 8");
    }

    private void setHeartClickListener(final ImageView heart, final String lesson) {
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favoriteLessons.contains(lesson)) {
                    heart.setImageResource(R.drawable.black_heart);
                    favoriteLessons.remove(lesson);  // Remove from favorites
                    Toast.makeText(PronunciationActivity.this, lesson + " removed from Favorites", Toast.LENGTH_SHORT).show();
                } else {
                    heart.setImageResource(R.drawable.red_heart);
                    favoriteLessons.add(lesson);  // Add to favorites
                    Toast.makeText(PronunciationActivity.this, lesson + " added to Favorites", Toast.LENGTH_SHORT).show();
                }

                // Save updated favorites to SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("FavoritesPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("favorites", favoriteLessons);
                editor.apply();
            }
        });
    }

    private void initializeHeartStates(ImageView heart, String lesson) {
        if (favoriteLessons.contains(lesson)) {
            heart.setImageResource(R.drawable.red_heart); // Set to red if favorite
        } else {
            heart.setImageResource(R.drawable.black_heart); // Set to black if not favorite
        }
    }

    public void goToFavorites(View view) {
        Intent intent = new Intent(this, FavouriteActivity.class);
        startActivity(intent);
    }
}
