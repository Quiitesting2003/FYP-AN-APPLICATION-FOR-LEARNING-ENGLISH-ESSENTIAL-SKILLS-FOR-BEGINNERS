package com.elearning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.elearning.HomePage.HomeActivity;
import com.elearning.Intro.Intro1Activity;
import com.elearning.Lauching.LauchingActivity;
import com.elearning.Survey.FirstSurveyActivity;
import com.elearning.Viet.Intro1VnActivity;
import com.elearning.Viet.SignInVnActivity;
import com.elearning.Viet.SignUpVnActivity;

public class MainActivity extends AppCompatActivity {

    private Button vnMainLanguage;
    private Button elMainLanguage;
    private String selectedLanguage = null; // Variable to store the selected language

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the views
        vnMainLanguage = findViewById(R.id.vn_main_language);
        elMainLanguage = findViewById(R.id.el_main_language);

        // Set onClickListener for the Vietnamese language selection
        vnMainLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedLanguage = "Vietnamese"; // Set the selected language
                Toast.makeText(MainActivity.this, "Đã chọn ngôn ngữ Tiếng Việt", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Intro1VnActivity.class);
                intent.putExtra("LANGUAGE", selectedLanguage); // Pass the selected language to the next activity
                startActivity(intent);
            }
        });

        // Set onClickListener for the English language selection
        elMainLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedLanguage = "English"; // Set the selected language
                Toast.makeText(MainActivity.this, "English is selected", Toast.LENGTH_SHORT).show();
                Intent intent  = new Intent(MainActivity.this, Intro1Activity.class);
                intent.putExtra("LANGUAGE", selectedLanguage); // Pass the selected language to the next activity
                startActivity(intent);
            }
        });

    }
}
