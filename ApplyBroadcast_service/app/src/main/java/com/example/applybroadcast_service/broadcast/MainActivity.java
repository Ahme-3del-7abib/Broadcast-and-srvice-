package com.example.applybroadcast_service.broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.example.applybroadcast_service.service.MyService;
import com.example.applybroadcast_service.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Switch aSwitch;
    database database;

    Button start_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new database(this);


        start_service = findViewById(R.id.StartServiceBtn);
        start_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startService(new Intent(MainActivity.this, MyService.class));
            }
        });


        aSwitch = findViewById(R.id.repeat_switch);
        String last_status = database.getLastStatus("5");
        if (last_status.equals("1")) {
            aSwitch.setChecked(true);
        } else {
            aSwitch.setChecked(false);
        }
    }

    public void onSwitchRepeat(View view) {

        boolean on = ((Switch) view).isChecked();
        if (on) {
            int check = database.checkEmpty();
            if (check > 0) {
                database.statusUpdate("5", 1);
                startNotification();
            } else {
                // insert
                database.dataInsert("5", 1);
                // set alarm
                startNotification();
            }
        } else {
            database.statusUpdate("5", 0);
            // second cancel alarm
            cancelNotification();
        }
    }

    private void cancelNotification() {

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(getApplicationContext(), MyReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 100, myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);

    }


    private void startNotification() {

        String systemTime;
        systemTime = getTime();
        int Hour = Integer.parseInt(systemTime.substring(0, 2).trim());
        int Minute = Integer.parseInt(systemTime.substring(3).trim());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Hour);
        calendar.set(Calendar.MINUTE, Minute);
        calendar.set(Calendar.SECOND, 59);

        Intent myIntent = new Intent(getApplicationContext(), MyReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 100, myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //  send notification after  60 minutes and repeated. --> AlarmManager.INTERVAL_HOUR
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, pendingIntent);
    }

    public String getTime() {

        String Time;
        Calendar calander;
        SimpleDateFormat simpledateformat;
        calander = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("HH:mm");
        Time = simpledateformat.format(calander.getTime());
        return Time;

    }
}