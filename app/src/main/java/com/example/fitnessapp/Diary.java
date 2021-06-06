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
import android.widget.Toast;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Diary extends AppCompatActivity {

    ImageView iv_homeIcon, iv_mealsIcon, iv_diaryIcon, iv_exerciseIcon, iv_infoIcon;
    TextView tv_heading, calories_Eaten, calories_Left;
    EditText manual_calories, api_calories, api_amount;
    Button calc_button, instruction;

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
        api_amount = findViewById(R.id.calculate_amount);
        calc_button = findViewById(R.id.button_calc);
        instruction = findViewById(R.id.diary_instruction);
        tv_heading = findViewById(R.id.id_diary_textView_heading);

        calories_Left.setText(""+ (Data.getCalories()-Data.getCalories()));

        instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Diary.this, DiaryInstructions.class));
            }
        });

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
                if(manual_calories.getText().toString().equals("Input") && !api_calories.getText().toString().equals("Food")) { //or whatever manual calories text is set to
                    DownloadFilesTask downloadFilesTask = new DownloadFilesTask();
                    downloadFilesTask.execute();
                }
                else if(!manual_calories.getText().toString().equals("Input") && !api_calories.getText().toString().equals("Food")){ //when user uses both manual input and api input
                    double cal_manual = Double.valueOf(manual_calories.getText().toString());
                    Data.addCurrentCalories(cal_manual);
                    if(( Data.getCalories() - Data.getCurrentCalories())>0) {
                        double rounded_cal = Data.getCalories() - Data.getCurrentCalories();
                        rounded_cal = (double)(Math.round(rounded_cal*10.0) / 10.0) ;
                        calories_Left.setText(String.valueOf(rounded_cal));
                    }
                    else {
                        calories_Left.setText("0");
                        Toast.makeText(Diary.this, "All done eating!",Toast.LENGTH_SHORT).show();
                    }
                    calories_Eaten.setText(String.valueOf(Data.getCurrentCalories()));
                    DownloadFilesTask downloadFilesTask = new DownloadFilesTask();
                    downloadFilesTask.execute();
                }
                else if(!manual_calories.getText().toString().equals("Input") && api_calories.getText().toString().equals("Food")){ // only manual input - no asynctask
                    double cal_manual = Double.valueOf(manual_calories.getText().toString());
                    Data.addCurrentCalories(cal_manual);
                    calories_Eaten.setText(String.valueOf(Data.getCurrentCalories()));
                    if(( Data.getCalories() - Data.getCurrentCalories())>0) {
                        double rounded_cal = Data.getCalories() - Data.getCurrentCalories();
                        rounded_cal = (double)(Math.round(rounded_cal*10.0) / 10.0) ;
                        calories_Left.setText(String.valueOf(rounded_cal));
                    }
                    else {
                        calories_Left.setText("0");
                        Toast.makeText(Diary.this, "All done eating!",Toast.LENGTH_SHORT).show();
                    }
                }
                //motivation toast
                DateFormat df = new SimpleDateFormat("h a");
                String time = df.format(Calendar.getInstance().getTime());
                Log.d("TIME",time);
                Boolean early = true;
                if(time.length() ==5)
                    early = false;
                double c = Data.getCalories() - Data.getCurrentCalories();
                if(early) {
                    if (c > 2000 && c<3000 && !time.substring(2).equals("PM")) //1 am - 9 am
                        Toast.makeText(Diary.this, "You're right on track!", Toast.LENGTH_SHORT).show();
                    if (c > 3000 && !time.substring(2).equals("PM"))
                        Toast.makeText(Diary.this, "Lets get to work soon!", Toast.LENGTH_SHORT).show();
                    if (c<1000 && !time.substring(2).equals("PM"))
                        Toast.makeText(Diary.this, "Slow down!", Toast.LENGTH_SHORT).show();

                    if (c > 2000 && c<3000 && time.substring(2).equals("PM") && Integer.valueOf(time.substring(0,1))<4) //1 pm - 3 pm
                        Toast.makeText(Diary.this, "Right on track!", Toast.LENGTH_SHORT).show();
                    if (c > 3000 && time.substring(2).equals("PM") && Integer.valueOf(time.substring(0,1))<7 && Integer.valueOf(time.substring(0,1))>3) // 4pm - 6pm
                        Toast.makeText(Diary.this, "Lets get to work soon!", Toast.LENGTH_SHORT).show();
                    if (c<1000 && time.substring(2).equals("PM") && Integer.valueOf(time.substring(0,1))>6) //7pm - 9pm
                        Toast.makeText(Diary.this, "Great! Almost done!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (time.substring(3).equals("AM") && !time.substring(0,2).equals("12")){
                        if(c<2000 && c>1500)
                            Toast.makeText(Diary.this, "Great work!", Toast.LENGTH_SHORT).show();
                        if(c<1500)
                            Toast.makeText(Diary.this, "Slow down!", Toast.LENGTH_SHORT).show();
                        if(c>2500)
                            Toast.makeText(Diary.this, "Pick up the pace!", Toast.LENGTH_SHORT).show();
                    }
                    if(time.substring(3).equals("PM") && !time.substring(0,2).equals("12")){
                        if(c>1000)
                            Toast.makeText(Diary.this, "Don't worry! You'll get it tomorrow", Toast.LENGTH_SHORT).show();
                        if(c<300)
                            Toast.makeText(Diary.this, "Great!", Toast.LENGTH_SHORT).show();
                    }
                }
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
            for(int i=0; i<cal.length()+1; i++){ //gets calories for that food
                if(cal.startsWith(" ", i) || cal.substring(i,i+1).equals(","))
                    cal = cal.substring(0,i) + cal.substring(i+1);
            }
            double cal_total = Double.valueOf(cal) * Integer.valueOf(api_amount.getText().toString()); //multiplies by amount
            Data.addCurrentCalories( cal_total);

            calories_Eaten.setText(String.valueOf(Data.getCurrentCalories()));

            if(( Data.getCalories() - Data.getCurrentCalories())>0) {
                double rounded_cal = Data.getCalories() - Data.getCurrentCalories();
                rounded_cal = (double)(Math.round(rounded_cal*10.0) / 10.0) ;
                calories_Left.setText(String.valueOf(rounded_cal));
            }
            else {
                calories_Left.setText("0");
                Toast.makeText(Diary.this, "All done eating!",Toast.LENGTH_SHORT).show();
            }
        }
    }


}