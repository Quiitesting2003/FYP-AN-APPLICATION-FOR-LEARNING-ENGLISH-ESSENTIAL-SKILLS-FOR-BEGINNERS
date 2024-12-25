package com.elearning.DBoard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.elearning.HomePage.DashBoardActivity;
import com.elearning.Login.SignInActivity;
import com.elearning.Login.SignUpActivity;
import com.elearning.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScoreActivity extends AppCompatActivity {

    TextView scoreTV, totalTV;
    Button backBtn;
    int score, total;
    FirebaseUser user;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);

        score = getIntent().getIntExtra("score", 0);
        total = getIntent().getIntExtra("total", 0);

        scoreTV = findViewById(R.id.score);
        totalTV = findViewById(R.id.total);
        backBtn = findViewById(R.id.backS);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, DashBoardActivity.class);
                startActivity(intent);
            }
        });


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();

        scoreTV.setText(String.valueOf(score));
        totalTV.setText(String.valueOf(total));

        reference.child("Score").child(user.getUid()).child("result").setValue(score).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ScoreActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ScoreActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}