package com.elearning.HomePage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.elearning.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AboutInformationActivity extends AppCompatActivity {

    private ImageView profileImageView;
    private TextView fullNameTextView;
    private TextView dateOfBirthTextView;
    private TextView emailTextView;
    private TextView phoneNumberTextView;
    private TextView genderTextView;
    private Button backButton;
    private Button updateButton;

    // SharedPreferences for local storage
    private SharedPreferences sharedPreferences;

    // Firebase Realtime Database reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_information);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Initialize views
        profileImageView = findViewById(R.id.iv_avatar_home);
        fullNameTextView = findViewById(R.id.tv_full_name);
        dateOfBirthTextView = findViewById(R.id.tv_date_of_birth);
        emailTextView = findViewById(R.id.tv_email);
        phoneNumberTextView = findViewById(R.id.tv_phone_number);
        genderTextView = findViewById(R.id.tv_gender);
        backButton = findViewById(R.id.info_back);
        updateButton = findViewById(R.id.info_update);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        // Load user information
        loadUserInfo();

        // Sync user information to Firebase
        syncUserInfoToFirebase();

        // Set up button listeners
        updateButton.setOnClickListener(v -> {
            Intent intent = new Intent(AboutInformationActivity.this, AboutUpdateInfoActivity.class);
            startActivity(intent);
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(AboutInformationActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }

    private void loadUserInfo() {
        // Retrieve user information from SharedPreferences
        String fullName = sharedPreferences.getString("fullName", "N/A");
        String dateOfBirth = sharedPreferences.getString("dateOfBirth", "N/A");
        String email = sharedPreferences.getString("email", "N/A");
        String phoneNumber = sharedPreferences.getString("phoneNumber", "N/A");
        String gender = sharedPreferences.getString("gender", "N/A");
        String profileImageUri = sharedPreferences.getString("profileImageUri", null);

        // Set the TextViews with user info
        fullNameTextView.setText(fullName);
        dateOfBirthTextView.setText(dateOfBirth);
        emailTextView.setText(email);
        phoneNumberTextView.setText(phoneNumber);
        genderTextView.setText(gender);

        // Load the profile image if the URI is not null
        if (profileImageUri != null) {
            Uri uri = Uri.parse(profileImageUri);

            // Grant persistable URI permission if the app has permission
            try {
                getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } catch (SecurityException e) {
                e.printStackTrace();
                Toast.makeText(AboutInformationActivity.this, "Permission to access this image is accessed.", Toast.LENGTH_SHORT).show();
            }

            // Set the URI to the ImageView
            profileImageView.setImageURI(uri);
        } else {
            profileImageView.setImageResource(R.drawable.people_icon); // Set a default image if the URI is null
        }
    }


    private void syncUserInfoToFirebase() {
        // Check if user is logged in
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            Toast.makeText(AboutInformationActivity.this, "Please log in to sync your data.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve user information from SharedPreferences
        String fullName = sharedPreferences.getString("fullName", "N/A");
        String dateOfBirth = sharedPreferences.getString("dateOfBirth", "N/A");
        String email = sharedPreferences.getString("email", "N/A");
        String phoneNumber = sharedPreferences.getString("phoneNumber", "N/A");
        String gender = sharedPreferences.getString("gender", "N/A");
        String profileImageUri = sharedPreferences.getString("profileImageUri", null);

        // Create a user object to upload
        User user = new User(fullName, dateOfBirth, email, phoneNumber, gender, profileImageUri);

        // Get the current user's UID from FirebaseAuth
        String uid = auth.getCurrentUser().getUid();

        // Push the user data to Firebase under the UID
        databaseReference.child(uid).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(AboutInformationActivity.this, "User info synced to Firebase!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AboutInformationActivity.this, "Failed to sync user info.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // User data model
    public static class User {
        public String fullName, dateOfBirth, email, phoneNumber, gender, profileImageUri;

        // Default constructor required for Firebase
        public User() {
        }

        // Constructor
        public User(String fullName, String dateOfBirth, String email, String phoneNumber, String gender, String profileImageUri) {
            this.fullName = fullName;
            this.dateOfBirth = dateOfBirth;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.gender = gender;
            this.profileImageUri = profileImageUri;
        }
    }
}
