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

public class Meals extends AppCompatActivity {

    ImageView iv_homeIcon, iv_mealsIcon, iv_diaryIcon, iv_exerciseIcon, iv_infoIcon;
    TextView tv_heading, meals_yes;
    EditText meals_input, meals_amount;
    Button meals_check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        iv_homeIcon = findViewById(R.id.id_navigation_image_homeIcon);
        iv_mealsIcon = findViewById(R.id.id_navigation_image_mealsIcon);
        iv_diaryIcon = findViewById(R.id.id_navigation_image_diaryIcon);
        iv_exerciseIcon = findViewById(R.id.id_navigation_image_exerciseIcon);
        iv_infoIcon = findViewById(R.id.id_navigation_image_infoIcon);
        tv_heading = findViewById(R.id.id_meals_textView_heading);
        meals_check = findViewById(R.id.id_meals_check);
        meals_input = findViewById(R.id.id_meals_editText_input);
        meals_yes = findViewById(R.id.id_meals_approve);
        meals_amount = findViewById(R.id.meals_amount);
        iv_homeIcon.setOnClickListener(v -> {
            Intent intentHomeActivity = new Intent(Meals.this, Home.class);
            startActivity(intentHomeActivity);
        });
        iv_mealsIcon.setOnClickListener(v -> {

        });
        iv_diaryIcon.setOnClickListener(v -> {
            Intent intentDiaryActivity = new Intent(Meals.this, Diary.class);
            startActivityForResult(intentDiaryActivity, 1);
        });
        iv_exerciseIcon.setOnClickListener(v -> {
            Intent intentExerciseActivity = new Intent(Meals.this, Exercise.class);
            startActivityForResult(intentExerciseActivity, 1);
        });
        iv_infoIcon.setOnClickListener(v -> {
            Intent intentInfoActivity = new Intent(Meals.this, Info.class);
            startActivityForResult(intentInfoActivity, 1);
        });

        meals_check.setOnClickListener(new View.OnClickListener() { //start async
            @Override
            public void onClick(View v) {
                Meals.DownloadFilesTask downloadFilesTask = new Meals.DownloadFilesTask();
                downloadFilesTask.execute();
            }
        });

    }
    private class DownloadFilesTask extends AsyncTask<String, String, String>{
        String info ="";
        @Override
        protected String doInBackground(String... strings) {
            String food = meals_input.getText().toString();
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
        protected void onPostExecute(String s) {
            String cal = s;
            for(int i=0; i<cal.length()+1; i++){ //gets calories for that food
                if(cal.startsWith(" ", i) || cal.substring(i,i+1).equals(","))
                    cal = cal.substring(0,i) + cal.substring(i+1);
            }
            DateFormat df = new SimpleDateFormat("h a");
            String time = df.format(Calendar.getInstance().getTime());
            Boolean early = true;
            if(time.length() ==5)
                early = false;
            double c = Data.getCalories() - Data.getCurrentCalories() - Double.valueOf(cal)*Integer.valueOf(meals_amount.getText().toString());
            if(early) {
                if (c > 2000 && c<3000 && !time.substring(2).equals("PM")) //1 am - 9 am
                    meals_yes.setText("Go ahead! Right on track");
                if (c > 3000 && !time.substring(2).equals("PM"))
                    meals_yes.setText("Yes you can!");
                if (c<1000 && !time.substring(2).equals("PM"))
                    meals_yes.setText("NO! Going too fast "+Data.getName() +"!");

                if (c > 2000 && c<3000 && time.substring(2).equals("PM") && Integer.valueOf(time.substring(0,1))<4) //1 pm - 3 pm
                    meals_yes.setText("Yes you can "+Data.getName() +"!");
                if (c > 3000 && time.substring(2).equals("PM") && Integer.valueOf(time.substring(0,1))<4)
                    meals_yes.setText("Yes you can!");
                if (c<1000 && time.substring(2).equals("PM") && Integer.valueOf(time.substring(0,1))<4)
                    meals_yes.setText("NO! Going too fast "+Data.getName() +"!");

                if (c > 3000 && time.substring(2).equals("PM") && Integer.valueOf(time.substring(0,1))<7 && Integer.valueOf(time.substring(0,1))>3) // 4pm - 6pm
                    meals_yes.setText("Yes you can, but I would eat something heavier "+Data.getName() +"!");
                if (c > 2000 && c<3000 && time.substring(2).equals("PM") && Integer.valueOf(time.substring(0,1))<7 && Integer.valueOf(time.substring(0,1))>3)
                    meals_yes.setText("Yes you can! ");
                if (c<1000 && time.substring(2).equals("PM") && Integer.valueOf(time.substring(0,1))<7 && Integer.valueOf(time.substring(0,1))>3)
                    meals_yes.setText("NO! Going too fast "+Data.getName() +"!");

                if (c > 2000 && time.substring(2).equals("PM") && Integer.valueOf(time.substring(0,1))>6)
                    meals_yes.setText("No! Find something heavier "+Data.getName() +"!");
                if (c<1000 && time.substring(2).equals("PM") && Integer.valueOf(time.substring(0,1))>6) //7pm - 9pm
                    meals_yes.setText("Great selection "+Data.getName() +"!");
            }
            else{
                if (time.substring(3).equals("AM") && !time.substring(0,2).equals("12")){  //10am-12pm
                    if(c<2500 && c>1500)
                        meals_yes.setText("Yes you can! ");
                    if(c<1500)
                        meals_yes.setText("NO! Choose something lighter");
                    if(c>2500 )
                        meals_yes.setText("Great selection "+Data.getName() +"!");
                }
                if(time.substring(3).equals("PM") && !time.substring(0,2).equals("12")){ //10pm-12pm
                    if(c>1000)
                        meals_yes.setText("Yes you can! ");
                    if(c<1000)
                        meals_yes.setText("Great selection "+Data.getName() +"!");
                }
            }
        }
    }
}