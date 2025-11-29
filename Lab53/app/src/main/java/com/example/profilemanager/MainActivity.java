package com.example.profilemanager;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView avatarImage;
    EditText teamName, postalCode;

    ActivityResultLauncher<Intent> profileActivityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        avatarImage = findViewById(R.id.avatarImage);
        teamName = findViewById(R.id.teamName);
        postalCode = findViewById(R.id.postalCode);

        // Register the result launcher
        profileActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            int selectedImageResID = result.getData()
                                    .getIntExtra("selectedImage", R.drawable.flag_00);
                            avatarImage.setImageResource(selectedImageResID);
                        }
                    }
                }
        );

        avatarImage.setOnClickListener(v -> openProfileActivity());
    }

    private void openProfileActivity() {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        profileActivityLauncher.launch(intent);
    }

    public void openMaps(View view) {
        String address = postalCode.getText().toString().trim();

        if (address.isEmpty()) {
            Toast.makeText(this, "Please enter an address!", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri gmmIntentUri = Uri.parse("https://maps.google.com/maps?q=" + Uri.encode(address));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.mawps");
        startActivity(mapIntent);
    }
}
