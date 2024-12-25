package com.elearning.HomePage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.elearning.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    private ImageView homeAbout;
    private ImageView dashBoard;
    private ImageView favourite;
    private ImageView home;
    private ListView list;
    private ImageView notification;
    private EditText search;
    private Handler handler;
    private Runnable generateMessageRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize views
        homeAbout = findViewById(R.id.home_about);
        dashBoard = findViewById(R.id.home_dashboard);
        favourite = findViewById(R.id.home_fav);
        home = findViewById(R.id.home_home);
        list = findViewById(R.id.recent_course_list);
        notification = findViewById(R.id.home_Notification);
        search = findViewById(R.id.home_Search);

        // Set up handler for generating messages
        handler = new Handler();
        generateMessageRunnable = new Runnable() {
            @Override
            public void run() {
                // Generate a message and word every minute
                handler.postDelayed(this, 60000); // Re-run the task every minute
            }
        };

        // Start generating messages when the activity starts
        handler.post(generateMessageRunnable);

        // Set up onClick listeners
        notification.setOnClickListener(v -> showGeneratedMessage());

        search.setOnClickListener(v -> showSearchOptions());

        home.setOnClickListener(v -> {
            changeImageViewColor(home);
            Intent intent1 = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(intent1);
        });

        favourite.setOnClickListener(v -> {
            changeImageViewColor(favourite);
            Intent intent2 = new Intent(HomeActivity.this, DashBoardActivity.class);
            startActivity(intent2);
        });

        dashBoard.setOnClickListener(v -> {
            changeImageViewColor(dashBoard);
            Intent intent3 = new Intent(HomeActivity.this, DashBoardActivity.class);
            startActivity(intent3);
        });

        homeAbout.setOnClickListener(v -> {
            changeImageViewColor(homeAbout);
            Intent intent4 = new Intent(HomeActivity.this, AboutActivity.class);
            startActivity(intent4);
        });
    }

    private void changeImageViewColor(ImageView imageView) {
        // Apply the blue tint color when clicked
        imageView.setColorFilter(getResources().getColor(R.color.blue), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private void showSearchOptions() {
        // Creating a dialog to show search options
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Search Option");

        // Set up the options
        builder.setItems(new CharSequence[]{"Search Dashboard", "Dictionary"}, (dialog, which) -> {
            String searchQuery = search.getText().toString().trim();
            if (searchQuery.isEmpty()) {
                Toast.makeText(HomeActivity.this, "Please enter a search query", Toast.LENGTH_SHORT).show();
                return;
            }

            switch (which) {
                case 0: // "Search Dashboard"
                    performDashboardSearch(searchQuery);
                    break;
                case 1: // "Dictionary"
                    showDictionarySuggestions(searchQuery);  // Show word suggestions for dictionary search
                    break;
                default:
                    break;
            }
        });

        builder.create().show();
    }

    private void performDashboardSearch(String query) {
        // Implement the logic to search through the DashboardActivity content
        // For example, send the query to DashBoardActivity
        Intent intent = new Intent(HomeActivity.this, DashBoardActivity.class);
        intent.putExtra("search_query", query);  // Pass the search query to DashBoardActivity
        startActivity(intent);
    }

    private void showDictionarySuggestions(String query) {
        // Sample list of dictionary words (You should fetch this from a dictionary source)
        List<String> dictionaryWords = getDictionaryWords(query);

        if (dictionaryWords.isEmpty()) {
            Toast.makeText(HomeActivity.this, "No dictionary results found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an AlertDialog to display the list of dictionary words
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Select a word");

        // Convert the list to a CharSequence array
        String[] wordsArray = dictionaryWords.toArray(new String[0]);

        builder.setItems(wordsArray, (dialog, which) -> {
            String selectedWord = wordsArray[which];
            // Now you can handle the word selection (show definition, etc.)
            showWordDefinition(selectedWord);
        });

        builder.create().show();
    }

    private List<String> getDictionaryWords(String query) {
        // Sample logic: In reality, you might query a database or API for dictionary words
        List<String> words = new ArrayList<>();
        switch (query.toLowerCase()) {
            case "example":
                words.add("apple");
                words.add("banana");
                words.add("car");
                words.add("dog");
                words.add("elephant");
                words.add("fish");
                words.add("grape");
                words.add("house");
                words.add("ice");
                words.add("jacket");
                break;
            case "tech":
                words.add("smartphone");
                words.add("tablet");
                words.add("printer");
                words.add("scanner");
                words.add("software");
                words.add("hardware");
                words.add("driver");
                words.add("data");
                words.add("cloud");
                words.add("download");
                break;
            case "food":
                words.add("bread");
                words.add("cheese");
                words.add("milk");
                words.add("butter");
                words.add("egg");
                words.add("rice");
                words.add("pasta");
                words.add("pizza");
                words.add("soup");
                break;
            case "emotion":
                words.add("happy");
                words.add("sad");
                words.add("angry");
                words.add("excited");
                words.add("tired");
                words.add("scared");
                words.add("surprised");
                words.add("bored");
                words.add("nervous");
                break;
            case "animal":
                words.add("dog");
                words.add("cat");
                words.add("bird");
                words.add("fish");
                words.add("cow");
                words.add("horse");
                words.add("sheep");
                words.add("goat");
                words.add("rabbit");
                break;
            default:
                words.add("No results found. Try using categories like tech, food, animal, emotion.");
                break;
        }

        return words;
    }

    private void showWordDefinition(String word) {
        // Define the word definition and example
        String definition = "This is the definition of " + word + ".";
        String example = "For example: " + word + " is used in a sentence like this...";

        // Create a custom AlertDialog to show the word, definition, and example
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle(word);

        // Set the message (definition and example)
        builder.setMessage("Definition:\n" + definition + "\n\nExample:\n" + example);

        builder.setPositiveButton("OK", (dialog, which) -> {
            // Close the dialog
            dialog.dismiss();
        });

        builder.create().show();
    }

    private void showGeneratedMessage() {
        // Example engaging message and word generated
        String word = getRandomWord();
        String definition = "Definition of " + word + ": A placeholder definition to engage the user.";
        String message = "Keep learning! Here's a new word to help you expand your vocabulary:";

        // Create an AlertDialog to show the message and word
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Engagement Message");
        builder.setMessage(message + "\n\n" + word + "\n\n" + definition);

        builder.setPositiveButton("OK", (dialog, which) -> {
            // Close the dialog
            dialog.dismiss();
        });

        builder.create().show();
    }

    private String getRandomWord() {
        // Sample list of words for demonstration. Replace with a real dictionary API or source.
        List<String> words = new ArrayList<>();
        words.add("example");
        words.add("technology");
        words.add("achievement");
        words.add("resilience");
        words.add("enthusiasm");

        Random random = new Random();
        int index = random.nextInt(words.size());
        return words.get(index);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove any pending posts in the handler when activity is destroyed
        handler.removeCallbacks(generateMessageRunnable);
    }
}
