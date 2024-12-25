package com.elearning.VietHomePage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.elearning.Login.SignInActivity;
import com.elearning.R;
import com.elearning.Viet.SignInVnActivity;

public class AboutSettingVnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_setting_vn);

        // Find the switch button and terms button in the layout
        Switch languageSwitch = findViewById(R.id.language_switch);
        TextView termBtn = findViewById(R.id.setting_dk);
        Button backBtn = findViewById(R.id.setting_back);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutSettingVnActivity.this, AboutVnActivity.class);
                startActivity(intent);
            }
        });

        // Set listener for switch button changes
        languageSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Show confirmation dialog for language change
            new AlertDialog.Builder(AboutSettingVnActivity.this)
                    .setTitle("Thay đổi ngôn ngữ")
                    .setMessage("Bạn có muốn đổi ngôn ngữ Tiếng Anh không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        // Show second dialog for sign-in requirement
                        new AlertDialog.Builder(AboutSettingVnActivity.this)
                                .setTitle("Bắt buộc đăng nhập")
                                .setMessage("Vui lòng đăng nhập lại để thay đổi ngôn ngữ")
                                .setPositiveButton("OK", (dialog1, which1) -> {
                                    // Navigate to SignInVnActivity after confirmation
                                    Intent intent = new Intent(AboutSettingVnActivity.this, SignInActivity.class);
                                    startActivity(intent);
                                    finish(); // Optionally finish the current activity
                                })
                                .setCancelable(false)
                                .show();
                    })
                    .setNegativeButton("Không", (dialog, which) -> {
                        // User canceled the action, reset the switch if needed
                        languageSwitch.setChecked(false);
                    })
                    .setCancelable(true)
                    .show();
        });

        // Set up the terms button to show the terms of use
        termBtn.setOnClickListener(v -> showTermsDialog());
    }

    // Method to show the terms of use dialog
    private void showTermsDialog() {
        String terms = "Điều Khoản:\n\n"
                + "1. **Thỏa thuận người dùng**: Bằng cách sử dụng ứng dụng này, bạn đồng ý với các điều khoản này.\n\n"
                + "2. **Đủ điều kiện**: Ứng dụng này dành cho người dùng từ 13 tuổi trở lên. Người dùng dưới 13 tuổi chỉ có thể sử dụng ứng dụng "
                + "với sự đồng ý và giám sát của cha mẹ. Chúng tôi khuyến nghị cha mẹ xem xét các điều khoản sử dụng với con cái họ.\n\n"
                + "3. **Đăng ký tài khoản**: Bạn phải tạo một tài khoản để truy cập các tính năng nhất định. Bạn có trách nhiệm về "
                + "sự bảo mật thông tin đăng nhập của mình. Mọi việc sử dụng trái phép tài khoản của bạn nên được báo cáo ngay lập tức.\n\n"
                + "4. **Sử dụng nội dung**: Nội dung (bài học, quiz, âm thanh, v.v.) chỉ dành cho việc sử dụng cá nhân, giáo dục. Bạn không được "
                + "sao chép, chia sẻ hoặc sửa đổi bất kỳ nội dung nào của ứng dụng mà không có sự cho phép bằng văn bản từ chúng tôi.\n\n"
                + "5. **Tiến trình học tập**: Tiến trình của bạn được theo dõi để cải thiện trải nghiệm học tập của bạn. Tuy nhiên, dữ liệu này chỉ dành cho sử dụng cá nhân "
                + "và không tạo thành bất kỳ chứng nhận hoặc hồ sơ giáo dục chính thức nào.\n\n"
                + "6. **Đăng ký và thanh toán**: Các tính năng cao cấp yêu cầu một đăng ký. Có một tuần dùng thử miễn phí cho người dùng mới. "
                + "Tài khoản của bạn sẽ bị tính phí sau khi kết thúc thời gian dùng thử trừ khi đăng ký bị hủy. Bạn có thể hủy đăng ký bất kỳ lúc nào.\n\n"
                + "7. **Hoàn tiền**: Thanh toán đăng ký không được hoàn lại sau khi kết thúc thời gian dùng thử. Nếu bạn gặp sự cố hoặc cần hỗ trợ, "
                + "vui lòng liên hệ với đội ngũ hỗ trợ của chúng tôi.\n\n"
                + "8. **Chấm dứt sử dụng**: Chúng tôi có quyền chấm dứt hoặc tạm ngưng tài khoản của bạn nếu bạn vi phạm các điều khoản này hoặc tham gia vào các hoạt động trái pháp luật. "
                + "Khi chấm dứt, bạn có thể mất quyền truy cập vào dữ liệu và nội dung của bạn.\n\n"
                + "9. **Hành vi người dùng**: Bạn đồng ý không tham gia vào bất kỳ hoạt động nào làm gián đoạn dịch vụ của ứng dụng hoặc gây hại cho người dùng khác. "
                + "Điều này bao gồm việc đăng nội dung không phù hợp, giả mạo người khác hoặc tham gia vào hành vi quấy rối.\n\n"
                + "10. **Quyền riêng tư và thu thập dữ liệu**: Chúng tôi thu thập một số dữ liệu cá nhân để cải thiện trải nghiệm ứng dụng, bao gồm tên, email và tiến trình học tập. "
                + "Vui lòng tham khảo Chính sách bảo mật của chúng tôi để biết thông tin chi tiết về cách chúng tôi thu thập, sử dụng và bảo vệ dữ liệu của bạn.\n\n"
                + "11. **Giới hạn độ tuổi**: Người dùng dưới 13 tuổi chỉ có thể sử dụng ứng dụng với sự hướng dẫn của cha mẹ. Chúng tôi khuyến khích cha mẹ theo dõi hoạt động của con cái họ trong ứng dụng.\n\n"
                + "12. **Dịch vụ của bên thứ ba**: Chúng tôi có thể tích hợp các dịch vụ từ bên thứ ba (ví dụ: phân tích hoặc quảng cáo). Những dịch vụ này có các điều khoản riêng của chúng, và "
                + "chúng tôi khuyến khích người dùng xem xét các chính sách của họ. Chúng tôi không chịu trách nhiệm về hành động của các dịch vụ bên thứ ba.\n\n"
                + "13. **Sửa đổi điều khoản**: Chúng tôi có quyền sửa đổi các điều khoản sử dụng này bất kỳ lúc nào. Bạn sẽ được thông báo về những thay đổi quan trọng qua "
                + "email hoặc thông báo trong ứng dụng. Việc tiếp tục sử dụng ứng dụng sau khi có thay đổi ngụ ý rằng bạn chấp nhận các điều khoản mới.\n\n"
                + "14. **Quyền sở hữu trí tuệ**: Tất cả nội dung ứng dụng, logo và thiết kế đều là tài sản trí tuệ của [Tên ứng dụng]. Bạn không được sử dụng bất kỳ tài liệu nào "
                + "từ ứng dụng cho mục đích thương mại mà không có sự chấp thuận trước.\n\n"
                + "15. **Giới hạn trách nhiệm**: Chúng tôi cung cấp ứng dụng ‘theo trạng thái hiện có’ mà không có bất kỳ bảo đảm nào. Chúng tôi không chịu trách nhiệm cho bất kỳ thiệt hại nào xảy ra từ việc "
                + "sử dụng hoặc không thể sử dụng ứng dụng, bao gồm mất dữ liệu hoặc gián đoạn dịch vụ.\n\n"
                + "16. **Luật điều chỉnh**: Các điều khoản này được điều chỉnh bởi luật pháp của [Quốc gia]. Mọi tranh chấp phát sinh từ các điều khoản này sẽ được giải quyết theo luật pháp của [Quốc gia].\n\n"
                + "17. **Thông tin liên hệ**: Để có bất kỳ câu hỏi hoặc thắc mắc nào về các điều khoản này, vui lòng liên hệ với chúng tôi tại support@yourapp.com hoặc truy cập phần Trợ giúp trong ứng dụng.\n";

        new AlertDialog.Builder(AboutSettingVnActivity.this)
                .setTitle("Terms of Use")
                .setMessage(terms)
                .setPositiveButton("OK", null)
                .setCancelable(true)
                .show();
    }

    // Move openIntroPage() method outside onCreate()
    private void openIntroPage() {
        Intent intent = new Intent(AboutSettingVnActivity.this, HomeVnActivity.class);
        startActivity(intent);
    }
}
