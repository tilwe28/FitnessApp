package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Diary extends AppCompatActivity {

    ImageView iv_homeIcon, iv_mealsIcon, iv_diaryIcon, iv_exerciseIcon, iv_infoIcon;
    TextView tv_heading, calories_Eaten, calories_Left;
    EditText manual_calories, api_calories;
    Button calc_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        iv_homeIcon = findViewById(R.id.id_navigation_image_homeIcon);
        iv_mealsIcon = findViewById(R.id.id_navigation_image_mealsIcon);
        iv_diaryIcon = findViewById(R.id.id_navigation_image_diaryIcon);
        iv_exerciseIcon = findViewById(R.id.id_navigation_image_exerciseIcon);
        iv_infoIcon = findViewById(R.id.id_navigation_image_infoIcon);
        calories_Eaten = findViewById(R.id.calories_eaten);
        calories_Left = findViewById(R.id.calories_left);
        manual_calories = findViewById(R.id.diary_manual_calories);
        api_calories = findViewById(R.id.calculated_calories);
        calc_button = findViewById(R.id.button_calc);
        tv_heading = findViewById(R.id.id_diary_textView_heading);

        iv_homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHomeActivity = new Intent(Diary.this, Home.class);
                startActivity(intentHomeActivity);
            }
        });
        iv_mealsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMealsActivity = new Intent(Diary.this, Meals.class);
                startActivity(intentMealsActivity);
            }
        });
        iv_diaryIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        iv_exerciseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentExerciseActivity = new Intent(Diary.this, Exercise.class);
                startActivityForResult(intentExerciseActivity, 1);
            }
        });
        iv_infoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInfoActivity = new Intent(Diary.this, Info.class);
                startActivityForResult(intentInfoActivity, 1);
            }
        });
        calc_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadFilesTask downloadFilesTask = new DownloadFilesTask();
                downloadFilesTask.execute();
            }
        });



    }
    private class DownloadFilesTask extends AsyncTask<String, String, String> {
    String info ="";
        @Override
        protected String doInBackground(String... strings) {
            String food = api_calories.getText().toString();
            char sp = ' ';
            for(int i=0;i<food.length();i++)
                if(food.charAt(i) == sp)
                    food = food.substring(0,i)+"%20"+food.substring(i+1);

            URL myURL = null;
            try {
                myURL = new URL("https://api.edamam.com/api/food-database/v2/parser?ingr="+food+"&app_id=ebc7a8a2&app_key=6c75c2cebf796e153c326c698e101610");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            assert myURL != null;
            Log.d("TAG", myURL.toString());

            URLConnection connection = null;
            try {
                connection = myURL.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("TAG",e.toString());
            }
            Log.d("TAG", connection.toString());

            InputStream inputStream = null;
            try {
                inputStream = connection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("TAG",e.toString());
            }
            Log.d("TAG",inputStream.toString());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                String line = bufferedReader.readLine();
                boolean cont = true;
                do{
                    if(line.contains("KCAL")){
                        info += line.substring(line.indexOf(":")+1);
                        cont = false;
                    }
                    line = bufferedReader.readLine();
                }while(line !=null && cont);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("TAGB", info);
            return info;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            String cal = s;
            for(int i=0; i<cal.length()+1; i++){
                if(cal.startsWith(" ", i) || cal.substring(i,i+1).equals(","))
                    cal = cal.substring(0,i) + cal.substring(i+1);
            }
            calories_Eaten.setText(cal);
        }
    }


}