package com.elearning.Admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.elearning.Login.SignInActivity;

public class LogOut {

    private Context context;

    public LogOut(Context context) {
        this.context = context;
    }

    public void logOutUser() {
        // Clear saved user data in SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Display logout success message
        Toast.makeText(context, "You have been logged out.", Toast.LENGTH_SHORT).show();

        // Redirect to SignInActivity (or your login activity)
        Intent intent = new Intent(context, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
