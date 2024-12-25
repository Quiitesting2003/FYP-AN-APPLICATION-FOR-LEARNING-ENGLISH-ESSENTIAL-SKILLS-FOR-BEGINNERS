package com.elearning.Survey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elearning.HomePage.HomeActivity;
import com.elearning.R;

import java.util.HashSet;

public class ThirdSurveyActivity extends AppCompatActivity {

    // HashSet to store selected options
    private HashSet<String> selectedOptions = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_survey);

        Button skipButton = findViewById(R.id.third_skip);

        // Button views
        Button fiveMinsBtn = findViewById(R.id.five_mins);
        Button fifteenMinsBtn = findViewById(R.id.fifteen_mins);
        Button twentyFiveMinsBtn = findViewById(R.id.twentyfive_mins);
        Button thirtyFiveMinsBtn = findViewById(R.id.thirtyfive_mins);
        Button backBtn = findViewById(R.id.third_back);

        // Next button
        ImageView nextBtn = findViewById(R.id.thirdSurvey_next);

        // Setting up button click listeners for each choice
        setupButton(fiveMinsBtn, "5 minutes / day");
        setupButton(fifteenMinsBtn, "15 minutes / day");
        setupButton(twentyFiveMinsBtn, "25 minutes / day");
        setupButton(thirtyFiveMinsBtn, "35 minutes / day");

        skipButton.setOnClickListener(v -> {
            Intent intent = new Intent(ThirdSurveyActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ThirdSurveyActivity.this, SecondSurveyActivity.class);
            startActivity(intent);
        });

        // Handle next button click
        nextBtn.setOnClickListener(v -> {
            if (selectedOptions.isEmpty()) {
                Toast.makeText(ThirdSurveyActivity.this, "Please select at least one option to continue.", Toast.LENGTH_SHORT).show();
            } else {
                // Proceed to the HomeActivity if at least one option is selected
                Intent intent = new Intent(ThirdSurveyActivity.this, HomeActivity.class);
                // Optionally pass the selected options to the next activity
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