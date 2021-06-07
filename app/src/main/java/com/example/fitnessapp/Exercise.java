package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessActivities;
import com.google.android.gms.fitness.data.*;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Exercise extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks{

    ImageView iv_homeIcon, iv_mealsIcon, iv_diaryIcon, iv_exerciseIcon, iv_infoIcon;
    TextView tv_heading, tv_manualInput;
    EditText et_manualInput;

    GoogleApiClient mClient;
    SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

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
        et_manualInput = findViewById(R.id.id_exercise_editText_manualInput);

        et_manualInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    Data.addCaloriesBurned(Integer.parseInt(s.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACTIVITY_RECOGNITION, Manifest.permission.BODY_SENSORS}, 0);
            return;
        } else {
            Log.d("TAG", "Permissions accepted");
        }

        buildFitnessClient();
    }

    private void buildFitnessClient() {
        mClient = new GoogleApiClient.Builder(this).addApi(Fitness.HISTORY_API).addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ)).addConnectionCallbacks(this).useDefaultAccount().build();
        mClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        fetchUserGoogleFitData("2021-06-07");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void fetchUserGoogleFitData(String date) {
        if (mClient != null && mClient.isConnected() && mClient.hasConnectedApi(Fitness.HISTORY_API)) {

            Date d1 = null;
            try {
                d1 = originalFormat.parse(date);
            } catch (Exception ignored) {}
            Calendar calendar = Calendar.getInstance();

            try {
                calendar.setTime(d1);
            } catch (Exception e) {
                calendar.setTime(new Date());
            }

            DataReadRequest readRequest = queryDateFitnessData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            new CaloriesAsyncTask(readRequest, mClient).execute();

            Log.d("HistoryAPI", "Connected");

        } else Log.d("HistoryAPI", "Not connected");
    }

    private DataReadRequest queryDateFitnessData(int year, int month, int day_of_Month) {

        Calendar startCalendar = Calendar.getInstance(Locale.getDefault());

        startCalendar.set(Calendar.YEAR, year);
        startCalendar.set(Calendar.MONTH, month);
        startCalendar.set(Calendar.DAY_OF_MONTH, day_of_Month);

        startCalendar.set(Calendar.HOUR_OF_DAY, 23);
        startCalendar.set(Calendar.MINUTE, 59);
        startCalendar.set(Calendar.SECOND, 59);
        startCalendar.set(Calendar.MILLISECOND, 999);
        long endTime = startCalendar.getTimeInMillis();

        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);
        long startTime = startCalendar.getTimeInMillis();

        DataSource ESTIMATED_STEP_DELTAS = new com.google.android.gms.fitness.data.DataSource.Builder().setDataType(DataType.TYPE_STEP_COUNT_DELTA).setType(DataSource.TYPE_DERIVED).setStreamName("estimated_steps").setAppPackageName("com.google.android.gms").build();

        return new DataReadRequest.Builder().aggregate(DataType.TYPE_CALORIES_EXPENDED, DataType.AGGREGATE_CALORIES_EXPENDED).aggregate(ESTIMATED_STEP_DELTAS, DataType.AGGREGATE_STEP_COUNT_DELTA).bucketByActivitySegment(1, TimeUnit.MILLISECONDS).setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS).build();
    }

    private class CaloriesAsyncTask extends AsyncTask<Void, Void, Void> {

        Float expendedCalories = 0f;
        DataReadRequest readRequest;
        GoogleApiClient mClient = null;

        public CaloriesAsyncTask(DataReadRequest readRequest, GoogleApiClient mClient) {
            this.readRequest = readRequest;
            this.mClient = mClient;
        }

        @Override
        protected Void doInBackground(Void... params) {

            DataReadResult dataReadResult = Fitness.HistoryApi.readData(mClient, readRequest).await(1, TimeUnit.MINUTES);

            for (Bucket bucket : dataReadResult.getBuckets()) {
                String bucketActivity = bucket.getActivity();
                if (bucketActivity.contains(FitnessActivities.WALKING) || bucketActivity.contains(FitnessActivities.RUNNING)) {
                    List<DataSet> dataSets = bucket.getDataSets();
                    for (DataSet dataSet : dataSets)
                        if(dataSet.getDataType().getName().equals("com.google.calories.expended"))
                            for (DataPoint dp : dataSet.getDataPoints())
                                if (dp.getEndTime(TimeUnit.MILLISECONDS) > dp.getStartTime(TimeUnit.MILLISECONDS))
                                    for (Field field : dp.getDataType().getFields())
                                        expendedCalories += dp.getValue(field).asFloat();
                }
            }
            return null;
        }
        
        @Override
        protected void onPostExecute(Void dataReadResult) {
            super.onPostExecute(dataReadResult);
            Log.d("TAG", "Total calories: " + expendedCalories);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.d("TAG", "Permissions granted");
        }
    }

    public void clearText(View v) {
        EditText et = findViewById(v.getId());
        et.setText("");
    }

}