package com.elearning.VietHomePage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.elearning.R;
import androidx.activity.EdgeToEdge;

public class AboutPrivacyVnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_privacy_vn);

        // Find the TextView by its ID
        TextView btnPolicy = findViewById(R.id.privacy_policy);
        Button backBtn = findViewById(R.id.privacy_back);
        TextView changePass = findViewById(R.id.privacy_changePass);

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an AlertDialog to confirm the password change
                new AlertDialog.Builder(AboutPrivacyVnActivity.this)
                        .setTitle("Thay đổi mật khẩu")
                        .setMessage("Bạn có chắc chắn muốn thay đổi mật khẩu không?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // If the user confirms, start the ChangePasswordActivity
                                Intent intent = new Intent(AboutPrivacyVnActivity.this, ChangePasswordVnActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null) // Dismiss if "No" is clicked
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AboutPrivacyVnActivity.this, AboutVnActivity.class);
                startActivity(intent1);
            }
        });

        // Set an OnClickListener on the TextView (used as a button here)
        btnPolicy.setOnClickListener(view -> showPolicyDialog());
    }

    // Method to display the policy in an AlertDialog
    private void showPolicyDialog() {
        // Define the policy text
        String policyText = "Chính sách bảo mật:\n"
                + "1. Thu thập dữ liệu: Chúng tôi thu thập dữ liệu người dùng để cải thiện chức năng của ứng dụng. "
                + "Dữ liệu này được lưu trữ an toàn và không bao giờ được chia sẻ với bên thứ ba.\n"
                + "2. Quyền: Chúng tôi yêu cầu quyền truy cập vào micrô để thực hành giọng nói.\n"
                + "3. Đăng ký: Cần thanh toán cho các tính năng cao cấp, với quyền sử dụng dùng thử không giới hạn.\n"
                + "4. Độ chính xác của nội dung: Tất cả tài liệu học tập đều được cập nhật thường xuyên để đảm bảo độ chính xác.\n"
                + "5. Phản hồi: Bạn có thể báo cáo lỗi hoặc đề xuất các tính năng thông qua phần Trợ giúp.\n";

        // Create and show the AlertDialog
        new AlertDialog.Builder(this)
                .setTitle("App Policy")
                .setMessage(policyText)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }
}
