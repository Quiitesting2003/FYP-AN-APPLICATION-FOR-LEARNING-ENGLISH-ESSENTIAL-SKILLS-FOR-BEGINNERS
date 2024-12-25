package com.elearning.HomePage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.elearning.R;

public class AboutContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_contact);

        Button backBtn = findViewById(R.id.contact_back);
        TextView hotLine = findViewById(R.id.contact_hotline);
        TextView eMail = findViewById(R.id.contact_email);
        TextView fAq = findViewById(R.id.contact_faq);

        fAq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFaqDialog();
            }
        });

        eMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:support@gmail.com"));
                startActivity(emailIntent);
            }
        });

        hotLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123456789"));
                v.getContext().startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AboutContactActivity.this, AboutActivity.class);
                startActivity(intent1);
                finish(); // Finish the current activity to avoid stacking it
            }
        });
    }

    // Method to show FAQ dialog
    private void showFaqDialog() {
        // Scroll view for FAQ content
        ScrollView scrollView = new ScrollView(this);
        LinearLayout faqContentLayout = new LinearLayout(this);
        faqContentLayout.setOrientation(LinearLayout.VERTICAL);
        faqContentLayout.setPadding(20, 60, 20, 20);

        // FAQ questions and answers
        String[] questions = {
                "1. How do I start learning English?",
                "2. Which skill should I learn first?",
                "3. How can I pronounce English correctly?",
                "4. How should I learn vocabulary?",
                "5. How can I improve my English listening skills?",
                "6. How much grammar should I learn when starting out?",
                "7. How can I practice speaking English?",
                "8. How can I avoid forgetting the vocabulary I've learned?",
                "9. How do I stay motivated while learning English?",
                "10. Are there any resources suitable for Vietnamese learners of English?"
        };

        String[] answers = {
                "You can start with basic lessons on vocabulary and pronunciation, then gradually move on to other skills.",
                "You should begin with pronunciation and vocabulary. Once you master them, you can move on to listening and speaking skills.",
                "Practice pronunciation through instructional videos and try to mimic native speakers.",
                "Learn vocabulary by themes and use flashcard apps to help retain words for a longer time.",
                "Listen to short conversations and gradually increase difficulty by listening to music, podcasts, or watching movies.",
                "Learn basic grammar first, focusing on using common tenses like the present simple and past simple.",
                "You can practice speaking through apps or find friends and study groups to regularly practice speaking.",
                "Regularly review the vocabulary you've learned and use it in sentences or write short paragraphs to help retention.",
                "Set small daily goals and frequently track your learning progress. Don't forget to reward yourself when you achieve your goals!",
                "There are many materials and apps specifically for Vietnamese learners of English, such as books, mobile apps, or YouTube channels."
        };

        // Add questions and answers to the layout
        for (int i = 0; i < questions.length; i++) {
            TextView questionText = new TextView(this);
            questionText.setText(questions[i]);
            questionText.setTextSize(18);
            faqContentLayout.addView(questionText);

            TextView answerText = new TextView(this);
            answerText.setText(answers[i]);
            answerText.setTextSize(16);
            faqContentLayout.addView(answerText);
        }

        scrollView.addView(faqContentLayout);

        // Show FAQs in a dialog instead of replacing the content view
        new AlertDialog.Builder(this)
                .setTitle("FAQs")
                .setView(scrollView)
                .setPositiveButton("OK", null)
                .setCancelable(true)
                .show();
    }
}
