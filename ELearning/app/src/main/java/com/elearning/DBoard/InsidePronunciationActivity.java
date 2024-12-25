package com.elearning.DBoard;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.elearning.HomePage.DashBoardActivity;
import com.elearning.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class InsidePronunciationActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    private TextView questionTextView;
    private TextInputEditText answerInput;
    private Button backButton, checkAnswerButton, nextButton, shareButton;
    private LinearLayout correctLayout, wrongLayout;
    private ProgressBar progressBar;
    private ImageView voiceButton;

    private DatabaseReference reference;
    private String currentQuestion = "";
    private String correctAnswer = "";
    private int position = 0; // Current question index
    private int score = 0; // Track score
    private ArrayList<DataSnapshot> questionList = new ArrayList<>(); // To store Firebase questions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_pronunciation);

        // Initialize UI components
        questionTextView = findViewById(R.id.inPro_ques);
        answerInput = findViewById(R.id.tvInput);
        backButton = findViewById(R.id.inPro_back);
        checkAnswerButton = findViewById(R.id.tvButton);
        nextButton = findViewById(R.id.inPro_next);
        shareButton = findViewById(R.id.inPro_share);
        correctLayout = findViewById(R.id.correct);
        wrongLayout = findViewById(R.id.wrong);
        progressBar = findViewById(R.id.progressPro_Bar);
        voiceButton = findViewById(R.id.tvVoice);

        // Hide feedback layouts initially
        correctLayout.setVisibility(View.GONE);
        wrongLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        // Firebase Database Reference
        reference = FirebaseDatabase.getInstance().getReference();

        // Load all questions from Firebase
        loadQuestionsFromFirebase();

        // Back Button Logic
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(InsidePronunciationActivity.this, DashBoardActivity.class);
            startActivity(intent);
        });

        // Check Answer Button Logic
        checkAnswerButton.setOnClickListener(view -> checkAnswer());

        // Next Button Logic
        nextButton.setOnClickListener(view -> moveToNextQuestion());

        // Share Button Logic
        shareButton.setOnClickListener(view -> {
            if (!currentQuestion.isEmpty()) {
                String shareText = "Question: " + currentQuestion + "\nAnswer: " + correctAnswer;
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(intent, "Share via"));
            } else {
                Toast.makeText(this, "No question to share!", Toast.LENGTH_SHORT).show();
            }
        });

        // Speech-to-Text Button Logic
        voiceButton.setOnClickListener(view -> startSpeechToText());
    }

    private void loadQuestionsFromFirebase() {
        progressBar.setVisibility(View.VISIBLE);
        reference.child("Pronunciation").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                if (dataSnapshot.exists()) {
                    questionList.clear(); // Clear the list for fresh data
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        questionList.add(snapshot);
                    }
                    if (!questionList.isEmpty()) {
                        position = 0; // Reset position to the first question
                        loadQuestion();
                    } else {
                        Toast.makeText(InsidePronunciationActivity.this, "No questions available!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(InsidePronunciationActivity.this, "No data found in Firebase!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(InsidePronunciationActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadQuestion() {
        if (position < questionList.size()) {
            DataSnapshot questionSnapshot = questionList.get(position);
            currentQuestion = questionSnapshot.child("question").getValue(String.class);
            correctAnswer = questionSnapshot.child("answer").getValue(String.class);
            questionTextView.setText(currentQuestion);
            answerInput.setText("");
            correctLayout.setVisibility(View.GONE);
            wrongLayout.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "No more questions available!", Toast.LENGTH_SHORT).show();
        }
    }

    private void moveToNextQuestion() {
        if (position < questionList.size() - 1) {
            position++; // Move to the next question
            loadQuestion();
        } else {
            // If last question, go to ScoreActivity with score
            Intent intent = new Intent(InsidePronunciationActivity.this, ScoreActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("total", questionList.size());
            startActivity(intent);
            finish(); // Close current activity
        }
    }

    private void checkAnswer() {
        String userAnswer = answerInput.getText().toString().trim();
        if (userAnswer.isEmpty()) {
            Toast.makeText(this, "Please enter your answer!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            score++; // Increment score for correct answer
            correctLayout.setVisibility(View.VISIBLE);
            wrongLayout.setVisibility(View.GONE);
        } else {
            wrongLayout.setVisibility(View.VISIBLE);
            correctLayout.setVisibility(View.GONE);
        }
    }

    private void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, "Speech recognition is not supported on your device.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String spokenText = result.get(0);
                answerInput.setText(spokenText);
                checkAnswer(); // Check the answer after receiving speech input
            }
        }
    }
}
