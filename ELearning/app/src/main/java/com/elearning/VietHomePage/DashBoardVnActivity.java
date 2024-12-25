package com.elearning.VietHomePage;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.elearning.DBoard.GrammarActivity;
import com.elearning.DBoard.PronunciationActivity;
import com.elearning.DBoard.TranslateActivity;
import com.elearning.DBoard.VocabularyActivity;
import com.elearning.DBoardVN.GrammarVNActivity;
import com.elearning.DBoardVN.PronunciationVNActivity;
import com.elearning.DBoardVN.VocabularyVNActivity;
import com.elearning.R;

public class DashBoardVnActivity extends AppCompatActivity {

    private ImageView homeButton;
    private ImageView favButton;
    private ImageView dashboardButton;
    private ImageView aboutButton;
    private Button dbVocab;
    private Button dbPronunciation;
    private Button dbGrammar;
    private Button dbTranslate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dash_board_vn);

        dbVocab = findViewById(R.id.db_vocab);
        dbPronunciation = findViewById(R.id.db_pronunciation);
        dbGrammar = findViewById(R.id.db_grammar);
        homeButton = findViewById(R.id.about_home);
        dashboardButton = findViewById(R.id.about_dashboard);
        favButton = findViewById(R.id.about_fav);
        aboutButton = findViewById(R.id.about_about);
        dbTranslate = findViewById(R.id.db_translate);

        dbTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(DashBoardVnActivity.this, "com.example.translator"));
                startActivity(intent);
                finish();
            }
        });

        dbGrammar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardVnActivity.this, GrammarVNActivity.class);
                startActivity(intent);
                finish();
            }
        });


        dbPronunciation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardVnActivity.this, PronunciationVNActivity.class);
                startActivity(intent);
                finish();
            }
        });

        dbVocab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardVnActivity.this, VocabularyVNActivity.class);
                startActivity(intent);
                finish();
            }
        });


        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(aboutButton);

                Intent intent = new Intent(DashBoardVnActivity.this, AboutVnActivity.class);
                startActivity(intent);
                finish();
            }
        });


        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(favButton);

                Intent intent = new Intent(DashBoardVnActivity.this, FavouriteVnActivity.class);
                startActivity(intent);
                finish();
            }
        });

        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(dashboardButton);

                Intent intent = new Intent(DashBoardVnActivity.this, DashBoardVnActivity.class);
                startActivity(intent);
                finish();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(homeButton);

                Intent intent = new Intent(DashBoardVnActivity.this, HomeVnActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void changeImageViewColor(ImageView imageView) {
        // Apply the blue tint color when clicked
        imageView.setColorFilter(getResources().getColor(R.color.blue), android.graphics.PorterDuff.Mode.SRC_IN);
    }
}