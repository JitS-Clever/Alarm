package com.example.alarm;
import android.app.NotificationManager;        //added by CleverTap Assistant
import com.clevertap.android.sdk.CleverTapAPI; //added by CleverTap Assistant
import com.google.firebase.auth.FirebaseAuth;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button start;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInAnonymously();
        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());   //Initializing the CleverTap SDK
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		CleverTapAPI.createNotificationChannel(getApplicationContext(),"123","mychannel","lDescription",NotificationManager.IMPORTANCE_MAX,true);        //added by CleverTap Assistant
        start= findViewById(R.id.button);
        HashMap<String, Object> OnUserLoginProperties = new HashMap<String, Object>();//added by CleverTap Assistant
		OnUserLoginProperties.put("Email", "alarm@test.com");//added by CleverTap Assistant
        OnUserLoginProperties.put("Phone", "+914465123168");
        OnUserLoginProperties.put("Name", "Alarm");
		clevertapDefaultInstance.onUserLogin(OnUserLoginProperties);//added by CleverTap Assistant

        start.setOnClickListener(view -> startAlert());


//        String str = "08 07 2021 13:00:15.200";

//        LocalDateTime ldate = LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
//        Long time = ldate.toEpochSecond(ZoneOffset.UTC);

    }

    public void startAlert(){
        EditText text = findViewById(R.id.time);
        int i = Integer.parseInt(text.getText().toString());
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 123, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (i * 1000), pendingIntent);
        Toast.makeText(this, "Alarm set in " + i + " seconds",Toast.LENGTH_LONG).show();
    }
}
