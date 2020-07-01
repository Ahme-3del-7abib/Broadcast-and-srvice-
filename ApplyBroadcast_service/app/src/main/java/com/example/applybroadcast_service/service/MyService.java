package com.example.applybroadcast_service.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Trace;

import androidx.annotation.Nullable;

public class MyService extends Service {


    int mStartMode;
    IBinder mBindMode;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

         */
        Intent intent1 = new Intent(this, MyRecevier2.class);
        sendBroadcast(intent1);

        return mStartMode;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBindMode;
    }


}
