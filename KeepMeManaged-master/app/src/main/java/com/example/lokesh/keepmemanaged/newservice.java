package com.example.lokesh.keepmemanaged;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class newservice extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        ArrayList<String > hashMapArryaList=  intent.getStringArrayListExtra("array");
        String email=intent.getExtras().getString("key");
        String day=intent.getExtras().getString("day");
        Intent notificint=new Intent(getBaseContext(),NotificationPublisher.class);
        notificint.putExtra("key",email);
        notificint.putExtra("day",day);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(getBaseContext(),1,notificint,PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calender = Calendar.getInstance();
        calender.setTime(new Date());
        HashMap<String,Integer> ha=new HashMap<>();
        ha.put("monday",2);
        ha.put("tuesday",3);
        ha.put("wednesday",4);
        ha.put("thursday",5);
        ha.put("friday",6);
        int t = -1;
        int time;
        Iterator<String> iterator=hashMapArryaList.iterator();
        int i=0;
        int l=0;
        while (iterator.hasNext())
        {
            String place=iterator.next();
            i++;
            String venue=iterator.next();
            i++;
            if ((i-1)/2==0)
            {
                time=8;
            }
            else if ((i-1)/2==1)
            {
                time=9;
            }
            else if ((i-1)/2==2)
            {
                time=10;
            }
            else if ((i-1)/2==3)
            {
                time=11;
            }
            else if ((i-1)/2==4)
            {
                time=12;
            }
            else  if ((i-1)/2==5)
                time=2;
            else if ((i-1)/2==6)
                time=3;
            else
                time=4;
            if (!place.equals("FREE")&&!venue.equals("FREE")&&l==0)
            {
                if (time>=1&&time<=4)
                    t=time+12;
                else
                    t=time;
                l=1;
            }
        }
        if(t!=-1)
        {
            //Toast.makeText(TimeTable.this,t+"",Toast.LENGTH_LONG).show();
            Calendar s = Calendar.getInstance() ;
            s.set(Calendar.DAY_OF_WEEK, ha.get(day));
            s.set(Calendar.HOUR_OF_DAY, t+1);
            s.set(Calendar.MINUTE,58);
            s.set(Calendar.SECOND, 0);
            //Toast.makeText(DaysList.this,
            // sdf.format(calender.getTime()),Toast.LENGTH_LONG).show();
            //Toast.makeText(DaysList.this,sdf.format(s.getTime()),Toast.LENGTH_LONG).show();
            int x = (int) Math.abs(s.getTimeInMillis() - calender.getTimeInMillis());
            Toast.makeText(getApplicationContext(),x+"",Toast.LENGTH_LONG).show();
            AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calender.getTimeInMillis()+x,7*24*60*60*1000,pendingIntent);
            //Toast.makeText(SaveActivity.this,"Saved Successfully",Toast.LENGTH_LONG).show();
            //finish();
        }
        return START_STICKY;
    }
}
