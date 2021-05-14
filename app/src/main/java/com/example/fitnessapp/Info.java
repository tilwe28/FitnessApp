package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;

public class Info extends AppCompatActivity {

    ImageView iv_homeIcon, iv_mealsIcon, iv_diaryIcon, iv_exerciseIcon, iv_infoIcon;
    TextView tv_heading, tv_name, tv_height, tv_weight, tv_age, tv_weightGoal;
    EditText et_name, et_height, et_weight, et_age;
    RadioGroup rg_weightGoal;
    RadioButton rb_lose, rb_gain, rb_maintain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        iv_homeIcon = findViewById(R.id.id_navigation_image_homeIcon);
        iv_mealsIcon = findViewById(R.id.id_navigation_image_mealsIcon);
        iv_diaryIcon = findViewById(R.id.id_navigation_image_diaryIcon);
        iv_exerciseIcon = findViewById(R.id.id_navigation_image_exerciseIcon);
        iv_infoIcon = findViewById(R.id.id_navigation_image_infoIcon);

        iv_homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHomeActivity = new Intent(Info.this, Home.class);
                startActivity(intentHomeActivity);
            }
        });
        iv_mealsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMealsActivity = new Intent(Info.this, Meals.class);
                startActivity(intentMealsActivity);
            }
        });
        iv_diaryIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDiaryActivity = new Intent(Info.this, Diary.class);
                startActivity(intentDiaryActivity);
            }
        });
        iv_exerciseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentExerciseActivity = new Intent(Info.this, Exercise.class);
                startActivity(intentExerciseActivity);
            }
        });
        iv_infoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tv_heading = findViewById(R.id.id_info_textView_heading);
        tv_name = findViewById(R.id.id_info_textView_name);
        tv_height = findViewById(R.id.id_info_textView_height);
        tv_weight = findViewById(R.id.id_info_textView_weight);
        tv_age = findViewById(R.id.id_info_textView_age);
        et_name = findViewById(R.id.id_info_editText_name);
        et_height = findViewById(R.id.id_info_editText_height);
        et_weight = findViewById(R.id.id_info_editText_weight);
        et_age = findViewById(R.id.id_info_editText_age);
        rg_weightGoal = findViewById(R.id.id_info_radioGroup_weightGoal);
        rb_lose = findViewById(R.id.id_info_radioButton_lose);
        rb_gain = findViewById(R.id.id_info_radioButton_gain);
        rb_maintain = findViewById(R.id.id_info_radioButton_maintain);
    }

    public void clearText(View v) {
        EditText et = findViewById(v.getId());
        et.setText("");
    }
}