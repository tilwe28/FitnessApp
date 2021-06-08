package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Exercise extends AppCompatActivity {

    ImageView iv_homeIcon, iv_mealsIcon, iv_diaryIcon, iv_exerciseIcon, iv_infoIcon;
    TextView tv_heading, tv_manualInput, tv_activity, tv_distance;
    EditText et_manualInput, et_activity, et_distance;
    Button b_calculate;

    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        client = new OkHttpClient();

        iv_homeIcon = findViewById(R.id.id_navigation_image_homeIcon);
        iv_mealsIcon = findViewById(R.id.id_navigation_image_mealsIcon);
        iv_diaryIcon = findViewById(R.id.id_navigation_image_diaryIcon);
        iv_exerciseIcon = findViewById(R.id.id_navigation_image_exerciseIcon);
        iv_infoIcon = findViewById(R.id.id_navigation_image_infoIcon);

        iv_homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intentHomeActivity = new Intent(Exercise.this, Home.class);
                //startActivity(intentHomeActivity);
                finish();
            }
        });
        iv_mealsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMealsActivity = new Intent(Exercise.this, Meals.class);
                startActivity(intentMealsActivity);
            }
        });
        iv_diaryIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDiaryActivity = new Intent(Exercise.this, Diary.class);
                startActivity(intentDiaryActivity);
            }
        });
        iv_exerciseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        iv_infoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInfoActivity = new Intent(Exercise.this, Info.class);
                startActivityForResult(intentInfoActivity, 1);
            }
        });

        tv_heading = findViewById(R.id.id_exercise_textView_heading);
        tv_manualInput = findViewById(R.id.id_exercise_textView_manualInput);
        tv_activity = findViewById(R.id.id_exercise_textView_activity);
        tv_distance = findViewById(R.id.id_exercise_textView_distance);
        et_activity = findViewById(R.id.id_exercise_editText_activity);
        et_distance = findViewById(R.id.id_exercise_editText_distance);
        et_manualInput = findViewById(R.id.id_exercise_editText_manualInput);
        b_calculate = findViewById(R.id.id_exercise_button_calculate);

        et_manualInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("TAG", "Manually added");
                try {
                    Data.addCaloriesBurned(Integer.parseInt(s.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        b_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "button onClick");
                String s = et_distance.getText().toString() + " " + et_activity.getText().toString();
                Log.d("TAG", "String output:" + s);

                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                RequestBody body = RequestBody.create(mediaType, "query="+s+",age="+Data.getAge());
                Request request = new Request.Builder().url("https://trackapi.nutritionix.com/v2/natural/exercise").method("POST", body)
                        .addHeader("x-app-id", "9d193603")
                        .addHeader("x-app-key", "57a96cf27a47dac55b096244599b0cac")
                        .addHeader("content", "application/json")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Log.d("TAG", "onResponse");
                        if (!response.isSuccessful()) {
                            Log.d("TAG", "Unexpected code " + response.toString());
                            throw new IOException("Unexpected code " + response);
                        } else Log.d("TAG", "connection failed");

                        Log.d("TAG", "continuing");

                        final String responseData = response.body().string();

                        Exercise.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("TAG", "Updating textviews:" + responseData);
                            }
                        });
                    }
                });
            }
        });

    }

    public void clearText(View v) {
        EditText et = findViewById(v.getId());
        et.setText("");
    }

}