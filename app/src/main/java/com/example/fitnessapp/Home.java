package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class Home extends AppCompatActivity {

    ImageView iv_homeIcon, iv_mealsIcon, iv_diaryIcon, iv_exerciseIcon, iv_infoIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        iv_homeIcon = findViewById(R.id.id_home_image_homeIcon);
        iv_mealsIcon = findViewById(R.id.id_home_image_mealsIcon);
        iv_diaryIcon = findViewById(R.id.id_home_image_diaryIcon);
        iv_exerciseIcon = findViewById(R.id.id_home_image_exerciseIcon);
        iv_infoIcon = findViewById(R.id.id_home_image_infoIcon);

        iv_homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        iv_mealsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMealsActivity = new Intent(Home.this, Meals.class);
                intentMealsActivity.putExtra("HEADING", "Suggested Meals");
                startActivityForResult(intentMealsActivity, 1);
            }
        });
        iv_diaryIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDiaryActivity = new Intent(Home.this, Diary.class);
                intentDiaryActivity.putExtra("HEADING", "Enter Meals");
                startActivityForResult(intentDiaryActivity, 1);
            }
        });
        iv_exerciseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentExerciseActivity = new Intent(Home.this, Exercise.class);
                intentExerciseActivity.putExtra("HEADING", "Exercise");
                startActivityForResult(intentExerciseActivity, 1);
            }
        });
        iv_infoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInfoActivity = new Intent(Home.this, Info.class);
                intentInfoActivity.putExtra("HEADING", "User Info");
                startActivityForResult(intentInfoActivity, 1);
            }
        });


    }
}