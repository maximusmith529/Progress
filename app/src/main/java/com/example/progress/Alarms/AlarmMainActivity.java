package com.example.progress.Alarms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.progress.MainActivity;
import com.example.progress.R;

public class AlarmMainActivity extends Activity {
    private static final String TAG = "AlarmMainActivity";
    // This is a handle so that we can call methods on our service
    private ScheduleClient scheduleClient;
    // This is the date picker used to select the date for our notification
    private DatePicker picker;
    Context context = this;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_main);

        // Create a new service client and bind our activity to this service
        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();

        // Get a reference to our date picker
        picker = (DatePicker) findViewById(R.id.scheduleTimePicker);
    }

    /**
     * This is the onClick called from the XML to set a new notification
     */
    public void onDateSelectedButtonClick(View v){
//        // Get the date from our datepicker
//        int day = picker.getDayOfMonth();
//        int month = picker.getMonth();
//        int year = picker.getYear();
//        // Create a new calendar set to the date chosen
//        // we set the time to midnight (i.e. the first minute of that day)
//        Calendar c = Calendar.getInstance();
//        c.set(year, month, day);
//        c.set(Calendar.HOUR_OF_DAY, new Date().getHours());
//        c.set(Calendar.MINUTE, new Date().getMinutes()+1);
//        c.set(Calendar.SECOND, new Date().getSeconds());
//        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
//        scheduleClient.setAlarmForNotification(c);
//        // Notify the user what they just did
//        Toast.makeText(this, "Notification set for: "+ day +"/"+ (month+1) +"/"+ year, Toast.LENGTH_SHORT).show();
//        Log.i(TAG,"Notification set for: "+ day +"/"+ (month+1) +"/"+ year);

        Calendar calendar = Calendar.getInstance();
// 8.00 (8 AM)
        calendar.set(Calendar.HOUR_OF_DAY, new Date().getHours());
        calendar.set(Calendar.MINUTE, new Date().getMinutes() + 1);
        calendar.set(Calendar.SECOND, new Date().getSeconds());
        Log.i(TAG,"Notification set for: "+ calendar.getTime().toString());
        PendingIntent pi = PendingIntent.getService(context, 0 , new Intent(context, MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);
    }

    @Override
    protected void onStop() {
        // When our activity is stopped ensure we also stop the connection to the service
        // this stops us leaking our activity into the system *bad*
        if(scheduleClient != null)
            scheduleClient.doUnbindService();
        super.onStop();
    }
}