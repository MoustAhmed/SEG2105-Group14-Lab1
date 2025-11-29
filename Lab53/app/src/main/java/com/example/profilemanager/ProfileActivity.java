package com.example.profilemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void SetTeamIcon(View view) {
        ImageView selectedImg = (ImageView) view;
        int resId = (int) selectedImg.getTag();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("selectedImage", resId);

        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
