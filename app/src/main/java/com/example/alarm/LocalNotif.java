package com.example.alarm;
import android.app.AlarmManager;
import android.app.NotificationManager;        //added by CleverTap Assistant
import androidx.annotation.NonNull;        //added by CleverTap Assistant

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.pushnotification.NotificationInfo;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class LocalNotif extends FirebaseMessagingService {
		CleverTapAPI clevertapDefaultInstance;        //added by CleverTap Assistant




    public void onMessageReceived(RemoteMessage message){
        try {
            if (message.getData().size() > 0) {
                Bundle extras = new Bundle();
                for (Map.Entry<String, String> entry : message.getData().entrySet()) {
                    extras.putString(entry.getKey(), entry.getValue());
                }

                NotificationInfo info = CleverTapAPI.getNotificationInfo(extras);

                if (info.fromCleverTap) {

                    String notifTime = extras.getString("Notif");

                    Long currentTime = System.currentTimeMillis();
                    Date epoch = null;
                    Long time;
                    try {
                        // epoch = new java.text.SimpleDateFormat ("MM/dd/yyyy HH:mm:ss").parse("01/01/1970 01:00:00");
                        epoch = new java.text.SimpleDateFormat("dd MM yyyy HH:mm:ss.SSS").parse(notifTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    time = (Long) epoch.getTime();
                    Long delayTime = time - currentTime;
                    Log.d("Epoch", time.toString());
                    Log.d("Current Time", currentTime.toString());

                    Intent intent = new Intent(this, MyBroadcastReceiver.class);
                    intent.putExtra("extra",extras);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            this.getApplicationContext(), 123, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                } else {
                    // not from CleverTap handle yourself or pass to another provider
                }
            }
        } catch (Throwable t) {
            Log.d("MYFCMLIST", "Error parsing FCM message", t);
        }
    }

	@Override
	public void onNewToken(@NonNull String s)
	{
		super.onNewToken(s);
		clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
		clevertapDefaultInstance.pushFcmRegistrationId(s,true);
	}
}
