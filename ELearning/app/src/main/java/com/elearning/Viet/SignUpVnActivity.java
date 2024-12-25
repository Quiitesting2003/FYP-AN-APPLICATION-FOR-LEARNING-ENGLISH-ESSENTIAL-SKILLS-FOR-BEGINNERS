package com.elearning.Viet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;

import com.elearning.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpVnActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private ImageView togglePasswordVisibilityImageView;
    private CheckBox termsAndConditionsCheckBox;
    private ImageView signUpButton;
    private ImageView googleImageView;
    private ImageView appleImageView;
    private ImageView haveAccountImageView;
    private TextView tvAgree;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_vn);

        // Initialize views
        emailEditText = findViewById(R.id.et_email_signUpVN);
        passwordEditText = findViewById(R.id.et_password_signUpVN);
        togglePasswordVisibilityImageView = findViewById(R.id.iv_toggle_password);
        termsAndConditionsCheckBox = findViewById(R.id.checkbox_signUpVN);
        signUpButton = findViewById(R.id.signUpVN);
        googleImageView = findViewById(R.id.gg_signUpVN);
        appleImageView = findViewById(R.id.apple_signUpVN);
        haveAccountImageView = findViewById(R.id.not_account_signUpVN);
        tvAgree = findViewById(R.id.tv_agree_vn);
        progressBar = findViewById(R.id.progress_bar);
        ImageView emailTickImageView = findViewById(R.id.iv_email_tick);

        // Add a TextWatcher to validate email
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String email = s.toString().trim();
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailTickImageView.setVisibility(View.VISIBLE);
                } else {
                    emailTickImageView.setVisibility(View.GONE);
                }
            }
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTermsAndConditionsDialog();
            }
        });

        // Set up listeners
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignUp();
            }
        });

        togglePasswordVisibilityImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordEditText.getTransformationMethod() instanceof android.text.method.PasswordTransformationMethod) {
                    passwordEditText.setTransformationMethod(null); // Show password
                    togglePasswordVisibilityImageView.setImageResource(R.drawable.visibilityoff);
                } else {
                    passwordEditText.setTransformationMethod(new android.text.method.PasswordTransformationMethod()); // Hide password
                    togglePasswordVisibilityImageView.setImageResource(R.drawable.visibilityoff);
                }
            }
        });

        haveAccountImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpVnActivity.this, SignInVnActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showTermsAndConditionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Điều khoản và điều kiện");
        String termsConditions = "Chấp nhận các điều khoản\n" +
                "Bằng cách tải xuống và sử dụng [Tên ứng dụng] (\"Ứng dụng\"), bạn đồng ý với các Điều khoản và Điều kiện này. Nếu bạn không đồng ý, vui lòng không sử dụng Ứng dụng.\n" +
                "\n" +
                "Giấy phép\n" +
                "[Tên ứng dụng] cấp cho bạn giấy phép có giới hạn, không độc quyền, không thể chuyển nhượng để sử dụng Ứng dụng cho mục đích cá nhân, phi thương mại. Bạn không được phép sửa đổi, phân phối hoặc tạo các tác phẩm phái sinh dựa trên Ứng dụng mà không được phép.\n" +                "\n" +
                "Bảo mật tài khoản\n" +
                "Bạn có trách nhiệm duy trì tính bảo mật của thông tin tài khoản của mình, bao gồm tên người dùng và mật khẩu. Bạn cũng chịu trách nhiệm cho mọi hoạt động trong tài khoản của mình. Hãy thông báo cho chúng tôi ngay lập tức nếu bạn nghi ngờ bất kỳ hành vi sử dụng trái phép nào đối với tài khoản của mình.\n" +                "\n" +
                "Nội dung\n" +
                "Tất cả nội dung trong Ứng dụng, bao gồm văn bản, hình ảnh và video, đều được bảo vệ bởi luật bản quyền và sở hữu trí tuệ. Bạn không được phép sao chép, phân phối hoặc khai thác bất kỳ phần nào của nội dung Ứng dụng mà không có sự đồng ý trước bằng văn bản.\n" +                "\n" +
                "Sửa đổi và Cập nhật\n" +
                "Chúng tôi có thể cập nhật, sửa đổi hoặc ngừng Ứng dụng bất kỳ lúc nào mà không cần thông báo. Chúng tôi không chịu trách nhiệm cho bất kỳ thay đổi, gián đoạn hoặc lỗi nào trong hoạt động của Ứng dụng.\n" +                "\n" +
                "Giới hạn trách nhiệm\n" +
                "Ứng dụng được cung cấp \"nguyên trạng\" và chúng tôi không chịu trách nhiệm cho bất kỳ thiệt hại hoặc mất mát nào phát sinh từ việc sử dụng ứng dụng. Để biết thêm thông tin, vui lòng tham khảo [Chính sách bảo mật] của chúng tôi. Bằng cách sử dụng Ứng dụng, bạn đồng ý với các điều khoản này.";
        builder.setMessage(termsConditions);

        builder.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SignUpVnActivity.this, "Bạn đã đồng ý với các Điều khoản và Điều kiện.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void handleSignUp() {
        progressBar.setVisibility(View.VISIBLE);
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền vào tất cả các trường", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(this, "Mật khẩu phải có 6 từ và chứa ít nhất 1 ký tự đặc biệt và 1 chữ cái", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        // Use Firebase to create an account
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign-up successful, redirect to SignInActivity
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignUpVnActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpVnActivity.this, SignInVnActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign-up fails, display a message to the user
                            Toast.makeText(SignUpVnActivity.this, "Đăng ký không thành công. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+=|<>?{}\\[\\]~-]).{8,}$";
        return password.matches(passwordPattern);
    }
}
