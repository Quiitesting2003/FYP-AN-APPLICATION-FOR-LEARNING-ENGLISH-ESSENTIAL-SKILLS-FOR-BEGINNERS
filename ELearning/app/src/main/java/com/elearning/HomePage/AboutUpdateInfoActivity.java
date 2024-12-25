package com.elearning.HomePage;

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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.elearning.R;

import java.util.Calendar;

public class AboutUpdateInfoActivity extends AppCompatActivity {

    private EditText fullNameEditText;
    private EditText dateOfBirthEditText;
    private EditText emailEditText;
    private EditText phoneNumberEditText;
    private Spinner genderSpinner;
    private ImageView avatarImageView;
    private Button saveButton;
    private Button backButton;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_update_info);

        // Initialize views
        avatarImageView = findViewById(R.id.update_avt);
        fullNameEditText = findViewById(R.id.update_name);
        dateOfBirthEditText = findViewById(R.id.update_dob);
        emailEditText = findViewById(R.id.update_email);
        phoneNumberEditText = findViewById(R.id.update_phone);
        genderSpinner = findViewById(R.id.update_gendere);
        saveButton = findViewById(R.id.update_save);
        backButton = findViewById(R.id.update_back);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        // Set up Date of Birth picker
        dateOfBirthEditText.setOnClickListener(v -> showDatePicker());

        // Set up Gender Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        // Load previous user information
        loadUserInfo();

        // Set up Phone Number TextWatcher
        phoneNumberEditText.addTextChangedListener(new PhoneTextWatcher());

        // Set up Avatar click listener
        avatarImageView.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                openImagePicker();
            }
        });

        // Set up Activity Result Launcher for picking image
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        if (imageUri != null) {
                            // Grant URI permission
                            grantUriPermission(getPackageName(), imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                            // Set the image to ImageView
                            avatarImageView.setImageURI(imageUri);

                            // Save URI as a tag for later use
                            avatarImageView.setTag(imageUri.toString());
                        }
                    }
                }
        );

        // Set up listeners
        backButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(AboutUpdateInfoActivity.this, AboutInformationActivity.class);
            startActivity(intent1);
            finish();
        });

        saveButton.setOnClickListener(v -> {
            if (handleProfileUpdate()) {
                Intent intent2 = new Intent(AboutUpdateInfoActivity.this, AboutInformationActivity.class);
                startActivity(intent2);
                finish();
            }
        });
    }

    private void loadUserInfo() {
        String fullName = sharedPreferences.getString("fullName", "");
        String dateOfBirth = sharedPreferences.getString("dateOfBirth", "");
        String email = sharedPreferences.getString("email", "");
        String phoneNumber = sharedPreferences.getString("phoneNumber", "");
        String gender = sharedPreferences.getString("gender", "Select Gender");

        fullNameEditText.setText(fullName);
        dateOfBirthEditText.setText(dateOfBirth);
        emailEditText.setText(email);
        phoneNumberEditText.setText(phoneNumber);

        // Set the spinner to the saved gender value
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) genderSpinner.getAdapter();
        int spinnerPosition = adapter.getPosition(gender);
        genderSpinner.setSelection(spinnerPosition);

// Load the profile image if available
        String profileImageUri = sharedPreferences.getString("profileImageUri", null);
        if (profileImageUri != null) {
            Uri imageUri = Uri.parse(profileImageUri);  // Parse the saved URI string into a Uri object
            avatarImageView.setImageURI(imageUri);      // Set the ImageView to the saved URI
            avatarImageView.setTag(imageUri.toString()); // Save the URI as a tag (as a string) for future use
        }
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String date = String.format("%02d/%02d/%04d", dayOfMonth, month1 + 1, year1);
            dateOfBirthEditText.setText(date);
        }, year, month, day);

        datePickerDialog.show();
    }

    private boolean handleProfileUpdate() {
        String fullName = fullNameEditText.getText().toString().trim();
        String dateOfBirth = dateOfBirthEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();
        String gender = genderSpinner.getSelectedItem().toString();

        // Validate input fields
        if (fullName.isEmpty()) {
            Toast.makeText(this, "Please fill in your full name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dateOfBirth.isEmpty()) {
            Toast.makeText(this, "Please fill in your date of birth", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Save user info to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("fullName", fullName);
        editor.putString("dateOfBirth", dateOfBirth);
        editor.putString("email", email);
        editor.putString("phoneNumber", phoneNumber);
        editor.putString("gender", gender);

        // Save image URI
        if (avatarImageView.getTag() != null) {
            editor.putString("profileImageUri", avatarImageView.getTag().toString());
        }

        // Apply changes to SharedPreferences
        editor.apply();

        // Show success message
        Toast.makeText(this, "Information updated successfully", Toast.LENGTH_SHORT).show();

        return true;
    }

    private void openImagePicker() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncher.launch(pickPhotoIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
                Toast.makeText(this, "Permission denied. Cannot access the library", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Nested class for phone number formatting
    private class PhoneTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String formatted = formatPhoneNumber(s.toString());
            if (!s.toString().equals(formatted)) {
                phoneNumberEditText.removeTextChangedListener(this);
                phoneNumberEditText.setText(formatted);
                phoneNumberEditText.setSelection(formatted.length());
                phoneNumberEditText.addTextChangedListener(this);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    }

    private String formatPhoneNumber(String phoneNumber) {
        String digits = phoneNumber.replaceAll("\\D", "");
        if (digits.length() <= 3) {
            return digits;
        } else if (digits.length() <= 6) {
            return digits.substring(0, 3) + "-" + digits.substring(3);
        } else {
            return digits.substring(0, 3) + "-" + digits.substring(3, 6) + "-" + digits.substring(6, Math.min(digits.length(), 10));
        }
    }
}
