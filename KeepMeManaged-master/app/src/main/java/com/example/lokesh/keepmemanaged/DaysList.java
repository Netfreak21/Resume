package com.example.lokesh.keepmemanaged;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DaysList extends AppCompatActivity {
    private PlaceHolderView mDrawerView;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private PlaceHolderView mGalleryView;
    String username,cource;
    String  a;
    String[] daynames = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"};
    int[] dayimages = {R.drawable.monday, R.drawable.tuesday, R.drawable.wed, R.drawable.wakeup, R.drawable.friday};
    ListView slv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days_list);

        //Notification part
        Intent notificint=new Intent(this,NotificationPublisher.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,notificint,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+19800000,pendingIntent);




        mDrawer = (DrawerLayout)findViewById(R.id.drawerLayout);
        mDrawerView = (PlaceHolderView)findViewById(R.id.drawerView);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mGalleryView = (PlaceHolderView)findViewById(R.id.galleryView);
        setupDrawer();

        // Creating list dynamically
        slv = (ListView) findViewById(R.id.slv);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        for (int i = 0; i < daynames.length; i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name", daynames[i]);
            hashMap.put("image", dayimages[i] + "");
            arrayList.add(hashMap);
        }
        String[] from = {"name", "image"};
        int[] to = {R.id.textview, R.id.imageview};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.list_view_items, from, to);
        slv.setAdapter(simpleAdapter);
        final String email=getIntent().getExtras().getString("key");
        slv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent=new Intent(DaysList.this,SaveActivity.class);
            Intent intent1=new Intent(DaysList.this,TimeTable.class);
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                intent.putExtra("key",getIntent().getExtras().getString("key"));
                intent1.putExtra("key",getIntent().getExtras().getString("key"));
                if(i==0)
                {

                    intent.putExtra("day", "monday");
                    intent1.putExtra("day", "monday");
                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                    DatabaseReference myref=database.getReference("Main");
                    myref.child(email).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user=dataSnapshot.getValue(User.class);
                            username=user.username;
                            cource=user.cource;
                            //Toast.makeText(getApplicationContext(),username+"daysList"+cource,Toast.LENGTH_SHORT).show();
                            if(dataSnapshot.hasChild("monday")) {
                                //Toast.makeText(DaysList.this, "Data Available", Toast.LENGTH_LONG).show();
                                int l=0;
                                DaysTable dt=dataSnapshot.child("monday").getValue(DaysTable.class);
                                Iterator<HashMap<String,String>> itr=dt.arr.iterator();
                                while (itr.hasNext())
                                {
                                    HashMap<String,String> h=itr.next();
                                    if (h.get("place").equals(""))
                                    {
                                        l=1;
                                        break;
                                    }
                                }
                                if (l==0)
                                    // Toast.makeText(DaysList.this, "Data Available", Toast.LENGTH_LONG).show();
                                    startActivity(intent1);
                                else {startActivity(intent);}
                            }
                            else
                                startActivity(intent);
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if(i==1)
                {
                    intent.putExtra("day", "tuesday");
                    intent1.putExtra("day", "tuesday");FirebaseDatabase database=FirebaseDatabase.getInstance();
                    DatabaseReference myref=database.getReference("Main");
                    myref.child(email).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user=dataSnapshot.getValue(User.class);
                            username=user.username;
                            cource=user.cource;
                            if(dataSnapshot.hasChild("tuesday")) {
                                int l=0;
                                DaysTable dt=dataSnapshot.child("tuesday").getValue(DaysTable.class);
                                Iterator<HashMap<String,String>> itr=dt.arr.iterator();
                                while (itr.hasNext())
                                {
                                    HashMap<String,String> h=itr.next();
                                    if (h.get("place").equals(""))
                                    {
                                        l=1;
                                        break;
                                    }
                                }
                                if (l==0)
                                    // Toast.makeText(DaysList.this, "Data Available", Toast.LENGTH_LONG).show();
                                    startActivity(intent1);
                                else {startActivity(intent);}
                            }
                            else
                                startActivity(intent);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if(i==2)
                {
                    intent.putExtra("day", "wednesday");
                    intent1.putExtra("day", "wednesday");
                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                    DatabaseReference myref=database.getReference("Main");
                    myref.child(email).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user=dataSnapshot.getValue(User.class);
                            username=user.username;
                            cource=user.cource;
                            if(dataSnapshot.hasChild("wednesday")) {
                                int l=0;
                                DaysTable dt=dataSnapshot.child("wednesday").getValue(DaysTable.class);
                                Iterator<HashMap<String,String>> itr=dt.arr.iterator();
                                while (itr.hasNext())
                                {
                                    HashMap<String,String> h=itr.next();
                                    if (h.get("place").equals(""))
                                    {
                                        l=1;
                                        break;
                                    }
                                }
                                if (l==0)
                                    // Toast.makeText(DaysList.this, "Data Available", Toast.LENGTH_LONG).show();
                                    startActivity(intent1);
                                else {startActivity(intent);}
                            }
                            else
                                startActivity(intent);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if(i==3)
                {
                    intent.putExtra("day", "thursday");
                    intent1.putExtra("day", "thursday");
                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                    DatabaseReference myref=database.getReference("Main");
                    myref.child(email).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user=dataSnapshot.getValue(User.class);
                            username=user.username;
                            cource=user.cource;
                            if(dataSnapshot.hasChild("thursday")) {
                                int l=0;
                                DaysTable dt=dataSnapshot.child("thursday").getValue(DaysTable.class);
                                Iterator<HashMap<String,String>> itr=dt.arr.iterator();
                                while (itr.hasNext())
                                {
                                    HashMap<String,String> h=itr.next();
                                    if (h.get("place").equals(""))
                                    {
                                        l=1;
                                        break;
                                    }
                                }
                                if (l==0)
                                    // Toast.makeText(DaysList.this, "Data Available", Toast.LENGTH_LONG).show();
                                    startActivity(intent1);
                                else{ startActivity(intent);}
                            }
                            else
                                startActivity(intent);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    intent.putExtra("day", "friday");
                    intent1.putExtra("day", "friday");
                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                    DatabaseReference myref=database.getReference("Main");
                    myref.child(email).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user=dataSnapshot.getValue(User.class);
                            username=user.username;
                            cource=user.cource;
                            if(dataSnapshot.hasChild("friday")) {
                                int l=0;
                                DaysTable dt=dataSnapshot.child("friday").getValue(DaysTable.class);
                                Iterator<HashMap<String,String>> itr=dt.arr.iterator();
                                while (itr.hasNext())
                                {
                                    HashMap<String,String> h=itr.next();
                                    if (h.get("place").equals(""))
                                    {
                                        l=1;
                                        break;
                                    }
                                }
                                if (l==0)
                                    // Toast.makeText(DaysList.this, "Data Available", Toast.LENGTH_LONG).show();
                                    startActivity(intent1);
                                else {startActivity(intent);}
                            }
                            else
                                startActivity(intent);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                //startActivity(intent);
            }
        });
    }


    private void setupDrawer(){
        final String email=getIntent().getExtras().getString("key");
        String user,cour;
        user=getIntent().getExtras().getString("username");
        cour=getIntent().getExtras().getString("cource");
        //Toast.makeText(getApplicationContext(),user+"this"+cour,Toast.LENGTH_SHORT).show();
        mDrawerView
                .addView(new DrawerHeader(user,cour))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_PROFILE,email))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_ASSIGNMENTS,email))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_PROJECTS,email))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_SHARE,email))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_TERMS,email))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_SETTINGS,email))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_LOGOUT,email));

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.open_drawer, R.string.close_drawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }
}
