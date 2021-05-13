package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Diary extends AppCompatActivity {

    TextView tv_heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        tv_heading = findViewById(R.id.id_diary_textView_heading);

        tv_heading.setText(getIntent().getStringExtra("HEADING"));
    }
}