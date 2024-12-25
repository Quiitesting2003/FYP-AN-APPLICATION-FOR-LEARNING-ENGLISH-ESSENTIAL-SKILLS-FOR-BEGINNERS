package com.elearning.VietHomePage;

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

public class AboutContactVnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_contact_vn);

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
                Intent intent1 = new Intent(AboutContactVnActivity.this, AboutVnActivity.class);
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
                "1. Tôi bắt đầu học tiếng Anh như thế nào?",
                "2. Tôi nên học kỹ năng nào trước?",
                "3. Tôi có thể phát âm tiếng Anh đúng cách như thế nào?",
                "4. Tôi nên học từ vựng như thế nào?",
                "5. Tôi có thể cải thiện kỹ năng nghe tiếng Anh của mình như thế nào?",
                "6. Tôi nên học bao nhiêu ngữ pháp khi mới bắt đầu?",
                "7. Tôi có thể luyện nói tiếng Anh như thế nào?",
                "8. Tôi có thể tránh quên từ vựng đã học như thế nào?",
                "9. Tôi duy trì động lực khi học tiếng Anh như thế nào?",
                "10. Có nguồn tài liệu nào phù hợp với người Việt Nam học tiếng Anh không?"
        };

        String[] answers = {
                "Bạn có thể bắt đầu với các bài học cơ bản về từ vựng và phát âm, sau đó dần dần chuyển sang các kỹ năng khác",
                "Bạn nên bắt đầu với phát âm và từ vựng. Khi đã thành thạo, bạn có thể chuyển sang kỹ năng nghe và nói",
                "Luyện phát âm thông qua các video hướng dẫn và cố gắng bắt chước người bản xứ",
                "Học từ vựng theo chủ đề và sử dụng ứng dụng thẻ ghi nhớ để giúp ghi nhớ từ lâu hơn",
                "Nghe các cuộc hội thoại ngắn và tăng dần độ khó bằng cách nghe nhạc, podcast hoặc xem phim",
                "Trước tiên, hãy học ngữ pháp cơ bản, tập trung vào việc sử dụng các thì thông dụng như thì hiện tại đơn và thì quá khứ đơn",
                "Bạn có thể luyện nói thông qua các ứng dụng hoặc tìm bạn bè và nhóm học để luyện nói thường xuyên",
                "Thường xuyên ôn lại vốn từ vựng đã học và sử dụng thành câu hoặc viết đoạn văn ngắn để giúp ghi nhớ",
                "Đặt ra các mục tiêu nhỏ hàng ngày và thường xuyên theo dõi tiến trình học tập của bạn. Đừng quên tự thưởng cho bản thân khi đạt được mục tiêu!",
                "Có nhiều tài liệu và ứng dụng dành riêng cho người Việt Nam học tiếng Anh, chẳng hạn như sách, ứng dụng di động hoặc kênh YouTube"
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
