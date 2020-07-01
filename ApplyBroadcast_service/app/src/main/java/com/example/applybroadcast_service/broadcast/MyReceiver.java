package com.example.applybroadcast_service.broadcast;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.applybroadcast_service.R;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent notification = PendingIntent.getActivity(context,
                100, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify_001")
                .setContentIntent(notification)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Test Service")
                .setContentText("Hello world !")
                .setAutoCancel(true);

        // if canceled
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        manager.notify(100, builder.build());

    }
}
