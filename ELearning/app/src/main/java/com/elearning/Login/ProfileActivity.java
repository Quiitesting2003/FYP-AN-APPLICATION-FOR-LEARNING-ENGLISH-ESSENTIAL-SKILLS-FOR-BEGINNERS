package com.elearning.Login;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.elearning.R;
import com.elearning.Survey.FirstSurveyActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private EditText fullNameEditText, dateOfBirthEditText, emailEditText, phoneNumberEditText;
    private Spinner genderSpinner;
    private ImageView avatarImageView, profileContinueButton;
    private Button backButton;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private SharedPreferences sharedPreferences;
    private FirebaseUser user;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Firebase Auth and Database Reference
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish(); // close the activity if no user is logged in
            return;
        }

        // Initialize views
        avatarImageView = findViewById(R.id.iv_avatar_profile);
        fullNameEditText = findViewById(R.id.et_full_name);
        dateOfBirthEditText = findViewById(R.id.et_date_of_birth);
        emailEditText = findViewById(R.id.et_email);
        phoneNumberEditText = findViewById(R.id.et_phone_number);
        genderSpinner = findViewById(R.id.spinner_gender);
        profileContinueButton = findViewById(R.id.profile_continue_button);
        backButton = findViewById(R.id.back_profile_button);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        emailEditText.setText(sharedPreferences.getString("loggedInEmail", ""));

        // Retrieve the email from the Intent and set it to emailEditText
        String emailFromIntent = getIntent().getStringExtra("email");
        if (emailFromIntent != null) {
            emailEditText.setText(emailFromIntent);
        }

        // Set up Date of Birth picker
        dateOfBirthEditText.setOnClickListener(v -> showDatePicker());

        // Set up Gender Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        // Set up Phone Number TextWatcher
        phoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                String formatted = formatPhoneNumber(s.toString());
                if (!s.toString().equals(formatted)) {
                    phoneNumberEditText.setText(formatted);
                    phoneNumberEditText.setSelection(formatted.length());
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Set up Avatar click listener
        avatarImageView.setOnClickListener(v -> {
            Log.d("ProfileActivity", "Image clicked");
            requestImagePickerPermission();
        });

        // Set up Activity Result Launcher for picking image
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        avatarImageView.setImageURI(imageUri);
                        avatarImageView.setTag(imageUri.toString());
                    }
                }
        );

        // Load and save data from Firebase on button clicks
        backButton.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, SignInActivity.class)));
        profileContinueButton.setOnClickListener(v -> {
            if (handleProfileUpdate()) {
                startActivity(new Intent(ProfileActivity.this, FirstSurveyActivity.class));
            }
        });

        // Retrieve user data from Firebase
        retrieveUserDataFromFirebase();

        // Optionally set default values
        setDefaultValues();
    }

    private void setDefaultValues() {
        fullNameEditText.setText("");
        dateOfBirthEditText.setText("");
        phoneNumberEditText.setText("");
        genderSpinner.setSelection(0);
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) ->
                        dateOfBirthEditText.setText(String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private String formatPhoneNumber(String phoneNumber) {
        String digits = phoneNumber.replaceAll("\\D", "");
        if (digits.length() <= 3) return digits;
        else if (digits.length() <= 6) return digits.substring(0, 3) + "-" + digits.substring(3);
        else return digits.substring(0, 3) + "-" + digits.substring(3, 6) + "-" + digits.substring(6, Math.min(digits.length(), 10));
    }

    private boolean handleProfileUpdate() {
        String fullName = fullNameEditText.getText().toString().trim();
        String dateOfBirth = dateOfBirthEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();
        String gender = genderSpinner.getSelectedItem().toString();

        if (fullName.isEmpty() || dateOfBirth.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        saveUserDataToFirebase(fullName, dateOfBirth, email, phoneNumber, gender);
        return true;
    }

    private void saveUserDataToFirebase(String fullName, String dateOfBirth, String email, String phoneNumber, String gender) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("fullName", fullName);
        userData.put("dateOfBirth", dateOfBirth);
        userData.put("email", email);
        userData.put("phoneNumber", phoneNumber);
        userData.put("gender", gender);

        // Save URI to SharedPreferences and grant permission
        if (avatarImageView.getTag() != null) {
            String profileImageUri = avatarImageView.getTag().toString();
            sharedPreferences.edit().putString("profileImageUri", profileImageUri).apply();

            Uri uri = Uri.parse(profileImageUri);
            grantUriPermission(getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        // Save user data to Firebase Realtime Database
        databaseReference.updateChildren(userData)
                .addOnSuccessListener(aVoid -> {
                    // Save data to SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("fullName", fullName);
                    editor.putString("dateOfBirth", dateOfBirth);
                    editor.putString("email", email);
                    editor.putString("phoneNumber", phoneNumber);
                    editor.putString("gender", gender);
                    editor.putString("profileImageUri", avatarImageView.getTag() != null ? avatarImageView.getTag().toString() : null);
                    editor.apply();

                    Toast.makeText(this, "Information updated successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to update information", Toast.LENGTH_SHORT).show());
    }

    private void retrieveUserDataFromFirebase() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String fullName = dataSnapshot.child("fullName").getValue(String.class);
                    String dateOfBirth = dataSnapshot.child("dateOfBirth").getValue(String.class);
                    String phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                    String gender = dataSnapshot.child("gender").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);

                    fullNameEditText.setText(fullName);
                    dateOfBirthEditText.setText(dateOfBirth);
                    phoneNumberEditText.setText(phoneNumber);
                    emailEditText.setText(email);
                    genderSpinner.setSelection(((ArrayAdapter) genderSpinner.getAdapter()).getPosition(gender));
                }
            }

            @Override public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ProfileActivity", "Failed to read user data", databaseError.toException());
            }
        });
    }

    private void requestImagePickerPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            pickImageFromGallery();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncher.launch(intent);
    }
}
