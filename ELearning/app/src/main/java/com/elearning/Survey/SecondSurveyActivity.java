package com.elearning.Survey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elearning.R;

import java.util.HashSet;

public class SecondSurveyActivity extends AppCompatActivity {

    // HashSet to store selected options
    private HashSet<String> selectedOptions = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_survey);

        Button skipButton = findViewById(R.id.second_skip);

        // Button views - IDs are corrected here to match the layout file
        Button communicationBtn = findViewById(R.id.commnucation);
        Button educationBtn = findViewById(R.id.education);
        Button jobDevelopmentBtn = findViewById(R.id.job_development);
        Button timeManagementBtn = findViewById(R.id.time_management);
        Button migrationBtn = findViewById(R.id.migration);
        Button entertainmentBtn = findViewById(R.id.entertainment);
        Button schoolBtn = findViewById(R.id.school);
        Button backBtn = findViewById(R.id.second_back);
        Button highPayJobBtn = findViewById(R.id.high_job); // Corrected this ID as well
        Button otherBtn = findViewById(R.id.other);

        // Next button
        ImageView nextBtn = findViewById(R.id.secondSurvey_next);

        // Setting up button click listeners for each choice
        setupButton(communicationBtn, "Communication");
        setupButton(educationBtn, "Education");
        setupButton(jobDevelopmentBtn, "Job Development");
        setupButton(timeManagementBtn, "Time Management");
        setupButton(migrationBtn, "Migration");
        setupButton(entertainmentBtn, "Entertainment");
        setupButton(schoolBtn, "School");
        setupButton(highPayJobBtn, "High Paying Job");
        setupButton(otherBtn, "Other");

        // Skip button click listener
        skipButton.setOnClickListener(v -> {
            Intent intent = new Intent(SecondSurveyActivity.this, ThirdSurveyActivity.class);
            startActivity(intent);
        });

        // Back button click listener
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SecondSurveyActivity.this, FirstSurveyActivity.class);
            startActivity(intent);
        });

        // Handle next button click
        nextBtn.setOnClickListener(v -> {
            if (selectedOptions.isEmpty()) {
                // Prevent user from proceeding if no options are selected
                Toast.makeText(SecondSurveyActivity.this, "Please select at least one option to continue.", Toast.LENGTH_SHORT).show();
            } else {
                // Proceed to the next step if at least one option is selected
                Intent intent = new Intent(SecondSurveyActivity.this, ThirdSurveyActivity.class);
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