package com.example.applybroadcast_service.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyRecevier2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        Intent intent1 = new Intent(context, testServiceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);

    }
}
