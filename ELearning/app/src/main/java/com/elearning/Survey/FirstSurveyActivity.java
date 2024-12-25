package com.elearning.Survey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elearning.Login.ProfileActivity;
import com.elearning.R;

import java.util.HashSet;

public class FirstSurveyActivity extends AppCompatActivity {

    // HashSet to store selected options
    private HashSet<String> selectedOptions = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_survey);

        Button skipButton = findViewById(R.id.first_skip);

        // Button views - IDs are corrected here to match the layout file
        Button backBtn = findViewById(R.id.first_back);
        Button travelBtn = findViewById(R.id.travel);
        Button natureBtn = findViewById(R.id.nature);
        Button foodBtn = findViewById(R.id.food);
        Button drinkBtn = findViewById(R.id.drink);
        Button colorBtn = findViewById(R.id.color);
        Button weatherBtn = findViewById(R.id.weather);
        Button schoolBtn = findViewById(R.id.schooll);
        Button newsBtn = findViewById(R.id.news);
        Button artBtn = findViewById(R.id.art);
        Button techBtn = findViewById(R.id.tech);
        Button marketingBtn = findViewById(R.id.marketing);
        Button physicsBtn = findViewById(R.id.physics);
        Button familyBtn = findViewById(R.id.family);
        Button fitnessBtn = findViewById(R.id.fitness);
        Button designBtn = findViewById(R.id.design);
        Button clothesBtn = findViewById(R.id.clothes);
        Button otherBtn = findViewById(R.id.other);

        // Next button
        ImageView nextBtn = findViewById(R.id.firstSurvey_button);

        // Setting up button click listeners for each choice
        setupButton(backBtn, "Back");
        setupButton(travelBtn, "Travel");
        setupButton(natureBtn, "Nature");
        setupButton(foodBtn, "Food");
        setupButton(drinkBtn, "Drink");
        setupButton(colorBtn, "Color");
        setupButton(weatherBtn, "Weather");
        setupButton(schoolBtn, "School");
        setupButton(newsBtn, "News");
        setupButton(artBtn, "Art");
        setupButton(techBtn, "Technology");
        setupButton(marketingBtn, "Marketing");
        setupButton(physicsBtn, "Physics");
        setupButton(familyBtn, "Family");
        setupButton(fitnessBtn, "Fitness");
        setupButton(designBtn, "Design");
        setupButton(clothesBtn, "Clothes");
        setupButton(otherBtn, "Other");

        // Skip button click listener
        skipButton.setOnClickListener(v -> {
            Intent intent = new Intent(FirstSurveyActivity.this, SecondSurveyActivity.class);
            startActivity(intent);
        });

        // Back button click listener
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(FirstSurveyActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Handle next button click
        nextBtn.setOnClickListener(v -> {
            if (selectedOptions.isEmpty()) {
                // Prevent user from proceeding if no options are selected
                Toast.makeText(FirstSurveyActivity.this, "Please select at least one option to continue.", Toast.LENGTH_SHORT).show();
            } else {
                // Proceed to the next step if at least one option is selected
                Intent intent = new Intent(FirstSurveyActivity.this, SecondSurveyActivity.class);
                // Optionally, pass the selected options to the next activity
                intent.putExtra("selectedOptions", selectedOptions);
                startActivity(intent);
            }
        });
    }

    // Helper method to set up the button click listeners
    private void setupButton(Button button, String option) {
        button.setOnClickListener(v -> {
            if (selectedOptions.contains(option)) {
                selectedOptions.remove(option);
                button.setBackgroundColor(getResources().getColor(android.R.color.transparent)); // Reset button background
            } else {
                selectedOptions.add(option);
                button.setBackgroundColor(getResources().getColor(R.color.button_selected)); // Change button background on selection
            }
        });
    }
}