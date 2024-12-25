package com.elearning.DBoard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.SharedPreferences;


import androidx.appcompat.app.AppCompatActivity;

import com.elearning.HomePage.DashBoardActivity;
import com.elearning.R;

import java.util.HashSet;
import java.util.Set;

public class VocabularyActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    Button vocOne, vocTwo, vocBack;
    ImageView vocHeartOne, vocHeartTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);
        vocOne = findViewById(R.id.voc_one);
        vocTwo = findViewById(R.id.voc_two);
        vocBack = findViewById(R.id.voc_back);
        vocHeartOne = findViewById(R.id.voc_heart_one);
        vocHeartTwo = findViewById(R.id.voc_heart_two);

        vocTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VocabularyActivity.this, Vocab2Activity.class);
                startActivity(intent);
                finish();
            }
        });

        vocHeartTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("FavoritesPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Set<String> favorites = sharedPreferences.getStringSet("favorites", new HashSet<>());

                // Toggle heart image and update favorites
                if (vocHeartTwo.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.black_heart).getConstantState())) {
                    vocHeartTwo.setImageResource(R.drawable.red_heart);  // Change to red heart
                    favorites.add("Lesson 2");
                    Toast.makeText(VocabularyActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
                } else {
                    vocHeartTwo.setImageResource(R.drawable.black_heart);  // Change to black heart
                    favorites.remove("Lesson 2");
                    Toast.makeText(VocabularyActivity.this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
                }

                // Save the updated favorites to SharedPreferences
                editor.putStringSet("favorites", favorites);
                editor.apply();
            }
        });

        // Set OnClickListener for the "Back" button
        vocBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VocabularyActivity.this, DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set OnClickListener for the "Lesson 1" button
        vocOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to open InsideVocabularyActivity
                Intent intent = new Intent(VocabularyActivity.this, InsideVocabularyActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Optional: Set up the heart click listener (to toggle favorite status)
        vocHeartOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("FavoritesPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Set<String> favorites = sharedPreferences.getStringSet("favorites", new HashSet<>());

                // Toggle heart image and update favorites
                if (vocHeartOne.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.black_heart).getConstantState())) {
                    vocHeartOne.setImageResource(R.drawable.red_heart);  // Change to red heart
                    favorites.add("Lesson 1");
                    Toast.makeText(VocabularyActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
                } else {
                    vocHeartOne.setImageResource(R.drawable.black_heart);  // Change to black heart
                    favorites.remove("Lesson 1");
                    Toast.makeText(VocabularyActivity.this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
                }

                // Save the updated favorites to SharedPreferences
                editor.putStringSet("favorites", favorites);
                editor.apply();
            }
        });
    }
}