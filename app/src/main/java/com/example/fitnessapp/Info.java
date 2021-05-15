package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class Info extends AppCompatActivity {

    //widgets
    ImageView iv_homeIcon, iv_mealsIcon, iv_diaryIcon, iv_exerciseIcon, iv_infoIcon;
    TextView tv_heading, tv_name, tv_height, tv_weight, tv_age, tv_weightGoal, tv_gender, tv_activeness;
    EditText et_name, et_height, et_weight, et_age;
    RadioGroup rg_weightGoal, rg_gender, rg_activeness;
    RadioButton rb_lose, rb_gain, rb_maintain, rb_male, rb_female, rb_low, rb_average, rb_high;
    Button b_calculate;

    //attributes
    String name="";
    double height=0.0, weight=0.0, calories=0.0;
    int age=0, gender=0, goal=0, activeness=0;//gender 0=male 1=female,goal -1=lose 0=maintain 1=gain, activeness -1=low 0=average 1=high

    Context cont = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //navigation widgets
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

        //Info widgets
        tv_heading = findViewById(R.id.id_info_textView_heading);
        tv_name = findViewById(R.id.id_info_textView_name);
        tv_height = findViewById(R.id.id_info_textView_height);
        tv_weight = findViewById(R.id.id_info_textView_weight);
        tv_age = findViewById(R.id.id_info_textView_age);
        tv_weightGoal = findViewById(R.id.id_info_textView_weightGoal);
        tv_gender = findViewById(R.id.id_info_textView_gender);
        tv_activeness = findViewById(R.id.id_info_textView_activeness);
        et_name = findViewById(R.id.id_info_editText_name);
        et_height = findViewById(R.id.id_info_editText_height);
        et_weight = findViewById(R.id.id_info_editText_weight);
        et_age = findViewById(R.id.id_info_editText_age);
        rg_weightGoal = findViewById(R.id.id_info_radioGroup_weightGoal);
        rg_gender = findViewById(R.id.id_info_radioGroup_gender);
        rg_activeness = findViewById(R.id.id_info_radioGroup_activeness);
        rb_lose = findViewById(R.id.id_info_radioButton_lose);
        rb_gain = findViewById(R.id.id_info_radioButton_gain);
        rb_maintain = findViewById(R.id.id_info_radioButton_maintain);
        rb_male = findViewById(R.id.id_info_radioButton_male);
        rb_female = findViewById(R.id.id_info_radioButton_female);
        rb_low = findViewById(R.id.id_info_radioButton_low);
        rb_average = findViewById(R.id.id_info_radioButton_average);
        rb_high = findViewById(R.id.id_info_radioButton_high);
        b_calculate = findViewById(R.id.id_info_button_calculate);

        //receiving information
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                name.toString();
            }
        });
        et_height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    height = Double.parseDouble(s.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        et_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    weight = Double.parseDouble(s.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        et_age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    age = Integer.parseInt(s.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rb_male.getId())
                    gender = 0;
                else gender = 1;
            }
        });
        rg_weightGoal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rb_lose.getId())
                    goal = -1;
                else if (checkedId == rb_maintain.getId())
                    goal = 0;
                else goal = 1;
            }
        });
        rg_activeness.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rb_low.getId())
                    activeness = -1;
                else if (checkedId == rb_average.getId())
                    activeness = 0;
                else activeness = 1;
            }
        });

        //calculating calories
        b_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gender==0)
                    calories = 88.362 + (13.397*weight*.453592) + (4.799*height*2.54) - (5.677*age);
                else
                    calories = 447.593 + (9.247*weight*.453592) + (3.098*height*2.54) - (4.33*age);

                if (activeness==-1)
                    calories *= 1.2;
                else if (activeness==0)
                    calories *= 1.3;
                else calories *= 1.4;

                if (goal==-1)
                    calories -= 200;
                else if (goal==1)
                    calories += 200;
            }
        });
    }

    public void clearText(View v) {
        EditText et = findViewById(v.getId());
        et.setText("");
    }
}