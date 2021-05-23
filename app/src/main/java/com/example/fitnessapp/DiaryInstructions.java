package com.example.fitnessapp;

import android.os.Bundle;
import android.util.DisplayMetrics;

public class DiaryInstructions extends Diary{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutdiarypop);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width *.8) , (int)(height*.6));
    }
}
