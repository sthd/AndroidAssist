package com.er.aa.smartalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.er.aa.R;

import java.util.Calendar;

public class SmartAlarmActivity extends AppCompatActivity {

    private TimePicker alarmTimePicker;
    private Button setAlarmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_alarm);

        alarmTimePicker = findViewById(R.id.alarmTimePicker);
        setAlarmButton = findViewById(R.id.setAlarmButton);
        alarmTimePicker.setIs24HourView(Boolean.valueOf("true"));
        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleTimer();
            }
        });
    }



    private void scheduleTimerFullMinutes() {
        int selectedHour = alarmTimePicker.getHour();
        int selectedMinute = alarmTimePicker.getMinute();

        Calendar selectedTime = Calendar.getInstance();
        selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour);
        selectedTime.set(Calendar.MINUTE, selectedMinute);

        Calendar currentTime = Calendar.getInstance();

        long delayInMillis = selectedTime.getTimeInMillis() - currentTime.getTimeInMillis();

        if (delayInMillis <= 0) {
            // The selected time is in the past or almost now
            showToast("Selected time has already passed!");
        } else {
            showToast("the time that is left is: " + String.valueOf(delayInMillis));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showToast("Time has passed!");
                }
            }, delayInMillis);
        }
    }

    private void scheduleTimer() {
        int selectedHour = alarmTimePicker.getHour();
        int selectedMinute = alarmTimePicker.getMinute();

        Calendar selectedTime = Calendar.getInstance();
        selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour);
        selectedTime.set(Calendar.MINUTE, selectedMinute);
        selectedTime.set(Calendar.SECOND, 0);  // Reset seconds to zero for accuracy
        selectedTime.set(Calendar.MILLISECOND, 0);  // Reset milliseconds to zero for accuracy

        Calendar currentTime = Calendar.getInstance();

        long delayInMillis = selectedTime.getTimeInMillis() - currentTime.getTimeInMillis();

        if (delayInMillis <= 0) {
            // The selected time is in the past or almost now
            showToast("Selected time has already passed!");
        } else {
            showToast("the time that is left is: " + String.valueOf(delayInMillis));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showToast("Time has passed!");
                }
            }, delayInMillis);
        }
    }
    //private void scheduleTimer() {
    //    int selectedHour = alarmTimePicker.getHour();
    //    int selectedMinute = alarmTimePicker.getMinute();
    //
    //    Calendar selectedTime = Calendar.getInstance();
    //    selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour);
    //    selectedTime.set(Calendar.MINUTE, selectedMinute);
    //
    //    long alarmTimeMillis = selectedTime.getTimeInMillis();
    //
    //    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    //    Intent alarmIntent = new Intent(this, SmartAlarmReceiver.class);
    //    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
    //
    //    // Set the alarm using the AlarmManager
    //    alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTimeMillis, pendingIntent);
    //
    //    showToast("Alarm set for " + selectedHour + ":" + selectedMinute);
    //}

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}