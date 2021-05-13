package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Meals extends AppCompatActivity {

    TextView tv_heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        tv_heading = findViewById(R.id.id_meals_textView_heading);

        tv_heading.setText(getIntent().getStringExtra("HEADING"));
    }
}