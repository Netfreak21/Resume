package com.example.lokesh.keepmemanaged;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by lokesh on 1/7/18.
 */

public class NotificationPublisher extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String textContent="click to check timetable";
        Intent inte=new Intent(context,TimeTable.class);
        inte.putExtra("key",intent.getExtras().getString("key"));
        inte.putExtra("day",intent.getExtras().getString("day"));
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(inte);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(1,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context )
                .setSmallIcon(R.drawable.schooltimetable)
                .setContentTitle("It's class time").
                        setContentText(textContent).
                        setPriority(NotificationCompat.PRIORITY_DEFAULT).
                        setContentIntent(pendingIntent).setAutoCancel(true);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="sequence";
            String discription="discription";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NotificationChannel.DEFAULT_CHANNEL_ID, name, importance);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationManagerCompat notificationManage = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define
        notificationManage.notify(1, mBuilder.build());
    }
}
