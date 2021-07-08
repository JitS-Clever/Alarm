package com.example.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.clevertap.android.sdk.CleverTapAPI;

import java.text.ParseException;
import java.util.Date;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getBundleExtra("extra");

        CleverTapAPI.createNotification(context,extras);

//        NotificationCompat.Builder nm = new NotificationCompat.Builder(context, "123").addExtras(extras);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//        notificationManager.notify(123456, nm.build());

        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();
    }
}