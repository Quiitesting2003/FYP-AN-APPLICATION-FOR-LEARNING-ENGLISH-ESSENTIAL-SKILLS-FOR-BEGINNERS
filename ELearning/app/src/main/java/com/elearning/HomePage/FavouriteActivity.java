package com.elearning.HomePage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elearning.DBoard.VocabularyActivity;
import com.elearning.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavouriteActivity extends AppCompatActivity {

    private ImageView homeButton;
    private ImageView favButton;
    private ImageView dashboardButton;
    private ImageView aboutButton;
    private ListView favList;
    private List<String> favItems;
    private boolean isDoubleClick = false; // To track double-clicks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        favList = findViewById(R.id.fav);
        favItems = new ArrayList<>();

        homeButton = findViewById(R.id.about_home);
        dashboardButton = findViewById(R.id.about_dashboard);
        favButton = findViewById(R.id.about_fav);
        aboutButton = findViewById(R.id.about_about);

        // Load favorites when activity is created
        loadFavorites();
        setupBottomNavigation();

        // Set click listener for the ListView
        favList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = favItems.get(position);

                if (isDoubleClick) {
                    // Remove item on double-click
                    favItems.remove(selectedItem);
                    saveFavorites();
                    ((ArrayAdapter) favList.getAdapter()).notifyDataSetChanged();
                    Toast.makeText(FavouriteActivity.this, selectedItem + " removed from favorites", Toast.LENGTH_SHORT).show();
                    isDoubleClick = false;
                } else {
                    // Handle single click: Navigate to Intro1Activity
                    isDoubleClick = true;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isDoubleClick) {
                                Intent intent = new Intent(FavouriteActivity.this, VocabularyActivity.class);
                                intent.putExtra("selected_item", selectedItem); // Pass selected item if needed
                                startActivity(intent);
                                isDoubleClick = false;
                            }
                        }
                    }, 300); // Reset after 300ms
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavorites();
    }

    private void loadFavorites() {
        SharedPreferences sharedPreferences = getSharedPreferences("FavoritesPrefs", Context.MODE_PRIVATE);
        Set<String> favoriteSet = sharedPreferences.getStringSet("favorites", new HashSet<>());

        favItems.clear();  // Clear the list to avoid duplicates
        favItems.addAll(favoriteSet);  // Add all favorite items from SharedPreferences

        // Set up the ArrayAdapter to display the list of favorites
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,  // Simple layout for list item
                favItems  // List of favorite items
        );

        favList.setAdapter(adapter);
    }

    // Save favorites to SharedPreferences
    private void saveFavorites() {
        SharedPreferences sharedPreferences = getSharedPreferences("FavoritesPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("favorites", new HashSet<>(favItems));  // Convert list to set
        editor.apply();
    }

    // Set up bottom navigation buttons
    private void setupBottomNavigation() {
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(aboutButton);
                Intent intent = new Intent(FavouriteActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(favButton);
                Intent intent = new Intent(FavouriteActivity.this, FavouriteActivity.class);
                startActivity(intent);
            }
        });

        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(dashboardButton);
                Intent intent = new Intent(FavouriteActivity.this, DashBoardActivity.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageViewColor(homeButton);
                Intent intent = new Intent(FavouriteActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void changeImageViewColor(ImageView imageView) {
        // Apply the blue tint color when clicked
        imageView.setColorFilter(getResources().getColor(R.color.blue), android.graphics.PorterDuff.Mode.SRC_IN);
    }
}
