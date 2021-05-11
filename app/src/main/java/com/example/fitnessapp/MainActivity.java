package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    ImageView iv_home, iv_meals, iv_diary, iv_activity, iv_info;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_home = findViewById(R.id.id_image_home);
        iv_meals = findViewById(R.id.id_image_meals);
        iv_diary = findViewById(R.id.id_image_diary);
        iv_activity = findViewById(R.id.id_image_activity);
        iv_info = findViewById(R.id.id_image_personalInfo);

    }
}